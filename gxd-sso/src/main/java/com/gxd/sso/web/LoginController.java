package com.gxd.sso.web;
import com.gxd.sso.utils.CookieUtils;
import com.gxd.sso.utils.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@Controller
public class LoginController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private static final Map<String, String> credentials = new HashMap<>();

    @Resource
    private JwtUtils jwtUtils;

    public LoginController() {
        credentials.put("hellokoding", "hellokoding");
        credentials.put("hellosso", "hellosso");
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
    public String login(HttpServletResponse httpServletResponse, String username, String password, String redirect, Model model){
        if (username == null || !credentials.containsKey(username) || !credentials.get(username).equals(password)){
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
        //账号密码判断业务

        String token = jwtUtils.generateToken(signingKey, username);
        CookieUtils.create(httpServletResponse, jwtTokenCookieName, token, false, 604800, "127.0.0.1");

        return "redirect:" + redirect;
    }
}
