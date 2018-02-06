package com.gxd.sso.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieUtils {

    public  void create(HttpServletResponse httpServletResponse, String name, String value, Boolean secure, Integer maxAge, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);
        cookie.setHttpOnly(true);
        if (maxAge != null) {
            cookie.setMaxAge(maxAge);
        }
        cookie.setDomain(domain);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    public  void clear(HttpServletResponse httpServletResponse, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setDomain("localhost");
        httpServletResponse.addCookie(cookie);
    }

    public  void clear(HttpServletResponse httpServletResponse, String name,String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setDomain(domain);
        httpServletResponse.addCookie(cookie);
    }

    public  String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 获取当前请求所有cookie
     *
     * @return
     */
    public static Cookie[] getCookies(HttpServletRequest request) {
        return request.getCookies();
    }

    /* 删除cookies
     * <p/>
            *
            * @param request
     * @param response
     * @param name
     * @param path     必须设置，否则无效
     */
    public static String deleteCookie(HttpServletRequest request, HttpServletResponse response, String name, String path) {
        String value = null;
        Cookie[] cookies = getCookies(request);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    value = cookie.getValue();
                    cookie.setMaxAge(0);
                    cookie.setPath(path);
                    response.addCookie(cookie);
                }
            }
        }
        return value;
    }

    /**
     * 删除cookie
     *
     * @param cookie   需要删除的某个cookie
     * @param response 响应对象
     * @param domain
     * @param path
     */
    public static void deleteCookie(Cookie cookie, HttpServletResponse response, String domain, String path) {
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath(path);
            cookie.setDomain(domain);
            response.addCookie(cookie);
        }
    }

}
