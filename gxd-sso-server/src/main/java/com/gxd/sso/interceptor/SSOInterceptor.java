package com.gxd.sso.interceptor;

import com.gxd.common.annotation.SSOIgnore;
import com.gxd.common.model.Result;
import com.gxd.common.utils.FastJsonUtils;
import com.gxd.redis.utils.RedisUtils;
import com.gxd.sso.enums.LoginResponseCode;
import com.gxd.sso.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//import com.gxd.redis.utils.RedisUtils;

/**
 * @Author:gxd
 * @Description:
 * @Date: 18:10 2018/1/3
 * @Modified By:
 */
public class SSOInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(SSOInterceptor.class);

    private   String jwtTokenCookieName = "JWT-TOKEN";
    private   String signingKey = "signingKey";
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisUtils redisUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        /***** 是否跳过登录 ****/
        if (isIgnoreAuthentication(handler)) {
            return Boolean.TRUE;
        }
        Boolean result = Boolean.TRUE;
        Integer state = Result.INPUT;
        String message = "";
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        String userId = request.getHeader("userId");
        //token不存在
        if (StringUtils.isEmpty(token)) {
            request.setAttribute("state", "-1");
            request.setAttribute("message", LoginResponseCode.LOGIN_TOKEN_NOT_NULL);
            return false;
        }
        if (StringUtils.isEmpty(userId)) {
            request.setAttribute("state", "-2");
            request.setAttribute("message", LoginResponseCode.USERID_NOT_NULL);
            return false;
        }

        String uuid = jwtUtils.parseToken(request,jwtTokenCookieName, signingKey);
        //解密token后的loginId与用户传来的loginId判断是否一致
        if (!StringUtils.equals(uuid, userId)) {
            request.setAttribute("state", "-1");
            request.setAttribute("message", LoginResponseCode.USERID_NOT_UNAUTHORIZED);
            return false;
        }
        if (!result) {
            // 判断ajax 请求
            if (isAjaxRequest(request)) {
                Result ajaxResult = new Result();
                ajaxResult.setState(state);
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage(message);
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(501);
                PrintWriter pWriter = null;
                try {
                    pWriter = response.getWriter();
                    pWriter.write(FastJsonUtils.toJSONString(ajaxResult));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (pWriter != null) {
                        pWriter.flush();
                        pWriter.close();
                    }
                }
            } else {
                request.setAttribute("state", state);
                request.setAttribute("message", message);
                request.getRequestDispatcher("/toLogin").forward(request, response);
            }
        }
        //验证登录时间
        //RedisLoginDTO redisLogin = (RedisLoginDTO) redisUtils.getObject(uid);
        //if (null == redisLogin) {
        //    writer = response.getWriter();
        //    ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.RESPONSE_CODE_UNLOGIN_ERROR, false);
        //    responseMessage(response, writer, responseVO);
        //    return false;
        //}

        //if (!StringUtils.equals(token, redisLogin.getToken())) {
        //    writer = response.getWriter();
        //    ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.USERID_NOT_UNAUTHORIZED, false);
        //    responseMessage(response, writer, responseVO);
        //    return false;
        //}
        ////系统时间>有效期（说明已经超过有效期）
        //if (System.currentTimeMillis() > redisLogin.getRefTime()) {
        //    writer = response.getWriter();
        //    ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.LOGIN_TIME_EXP, false);
        //    responseMessage(response, writer, responseVO);
        //    return false;
        //}
        //重新刷新有效期
        //redisLogin = new RedisLoginDTO(uid, token, System.currentTimeMillis() + 60L * 1000L * 30L);
        //redisUtils.setObject(uid, redisLogin, 360000000);
        return result;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    /**
     * Description ： 判断是否是Ajax请求
     *
     * @param request
     * @return
     */
    public boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (StringUtils.isNotEmpty(requestType)) {
            return true;
        }
        return false;
    }
    /**
     * 是否忽略认证，用户登录等信息
     *
     * @param handler
     * @return
     */
    public boolean isIgnoreAuthentication(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            SSOIgnore methodAnnotation = handlerMethod.getMethodAnnotation(SSOIgnore.class);
            if (methodAnnotation == null) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        } else {
            return Boolean.FALSE;
        }
    }

}
