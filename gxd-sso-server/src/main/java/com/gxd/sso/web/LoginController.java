package com.gxd.sso.web;

import com.gxd.common.salt.Digests;
import com.gxd.common.utils.ImageCaptcha;
import com.gxd.redis.utils.RedisUtils;
import com.gxd.sso.model.LoginDTO;
import com.gxd.sso.utils.CookieUtils;
import com.gxd.sso.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private static final Map<String, String> credentials = new HashMap<>();

    @Autowired
    private CookieUtils cookieUtils;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;

    @Value("${cookie.maxAge}")
    private String cookieMaxAge;
    /**
     * 验证码key
     */
    @Value("${cookie.validKey:test}")
    private String validateCodeKey;

    public LoginController() {
        credentials.put("hellokoding", ",hellokoding");
        credentials.put("hellosso", ",hellosso");
    }

    @RequestMapping("/")
    public String home(){
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletResponse httpServletResponse, LoginDTO loginDTO, String redirect, Model model){
        if (loginDTO == null || !credentials.containsKey(loginDTO.getLoginAccount()) || !credentials.get(loginDTO.getLoginAccount()).equals(loginDTO.getPassword())){
            model.addAttribute("state", "-12");
            model.addAttribute("message", "Invalid username or password!");
            return "login";
        }
        Integer maxAge = Integer.valueOf(cookieMaxAge);
        String token = jwtUtils.generateToken(signingKey, loginDTO.getLoginAccount());
        cookieUtils.create(httpServletResponse, jwtTokenCookieName, token, false, maxAge, "localhost");

        return "redirect:" + redirect;
    }
    // 获取验证码
    @RequestMapping("/validateCode")
    public void createValidateCode(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 获取验证码信息
        ImageCaptcha vCode = new ImageCaptcha(82, 30, 4, 100);
        // 生成redis获取验证码的KEY保存到cookie中
        String validateCode = Digests.MD5Encode(UUID.randomUUID().toString());

        cookieUtils.create(resp, validateCodeKey, validateCode, false,null, "localhost");
        // 保存验证码到redis，时间为1分钟
        redisUtils.expire(validateCode, vCode.getCode(),60);
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        ServletOutputStream sos = null;
        try {
            sos = resp.getOutputStream();
            ImageIO.write(vCode.getBuffImg(), "jpeg", sos);
        } finally {
            if (sos != null) {
                sos.close();
            }
        }
    }
}
