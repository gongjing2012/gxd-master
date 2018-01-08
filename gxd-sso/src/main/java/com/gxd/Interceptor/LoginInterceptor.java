package com.gxd.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.gxd.annotation.IsLogin;
import com.gxd.enums.LoginResponseCode;
import com.gxd.enums.ResponseCode;
import com.gxd.model.RedisLoginDTO;
import com.gxd.model.ResponseVO;
import com.gxd.redis.utils.RedisUtils;
import com.gxd.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author:gxd
 * @Description:
 * @Date: 18:10 2018/1/3
 * @Modified By:
 */
public class LoginInterceptor implements HandlerInterceptor {

    private   String jwtTokenCookieName = "JWT-TOKEN";
    private   String signingKey = "signingKey";
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisUtils redisUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        PrintWriter writer = null;
        HandlerMethod method = null;
        try {
            method = (HandlerMethod) handler;
        } catch (Exception e) {
            writer = response.getWriter();
            ResponseVO responseVO = ResponseCode.buildEnumResponseVO(ResponseCode.REQUEST_URL_NOT_SERVICE, false);
            responseMessage(response, writer, responseVO);
            return false;
        }
        IsLogin isLogin = method.getMethodAnnotation(IsLogin.class);
        if (null == isLogin) {
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        String uid = request.getHeader("uid");
        //token不存在
        if (StringUtils.isEmpty(token)) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.LOGIN_TOKEN_NOT_NULL, false);
            responseMessage(response, writer, responseVO);
            return false;
        }
        if (StringUtils.isEmpty(uid)) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.USERID_NOT_NULL, false);
            responseMessage(response, writer, responseVO);
            return false;
        }

        String uuid = jwtUtils.parseToken(request,jwtTokenCookieName, signingKey);
        //解密token后的loginId与用户传来的loginId判断是否一致
        if (!StringUtils.equals(uuid, uid)) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.USERID_NOT_UNAUTHORIZED, false);
            responseMessage(response, writer, responseVO);
            return false;
        }

        //验证登录时间
        RedisLoginDTO redisLogin = (RedisLoginDTO) redisUtils.getObject(uid);
        if (null == redisLogin) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.RESPONSE_CODE_UNLOGIN_ERROR, false);
            responseMessage(response, writer, responseVO);
            return false;
        }

        if (!StringUtils.equals(token, redisLogin.getToken())) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.USERID_NOT_UNAUTHORIZED, false);
            responseMessage(response, writer, responseVO);
            return false;
        }
        //系统时间>有效期（说明已经超过有效期）
        if (System.currentTimeMillis() > redisLogin.getRefTime()) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.LOGIN_TIME_EXP, false);
            responseMessage(response, writer, responseVO);
            return false;
        }
        //重新刷新有效期
        redisLogin = new RedisLoginDTO(uid, token, System.currentTimeMillis() + 60L * 1000L * 30L);
        redisUtils.setObject(uid, redisLogin, 360000000);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    private void responseMessage(HttpServletResponse response, PrintWriter out, ResponseVO responseVO) {
        response.setContentType("application/json; charset=utf-8");
        JSONObject result = new JSONObject();
        result.put("result", responseVO);
        out.print(result);
        out.flush();
        out.close();
    }

}
