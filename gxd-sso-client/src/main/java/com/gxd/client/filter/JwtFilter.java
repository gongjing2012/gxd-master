package com.gxd.client.filter;

import com.gxd.client.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String username = JwtUtils.getSubject(httpServletRequest, jwtTokenCookieName, signingKey);
        log.debug("username:"+username);
        if(username == null){
            String authService = this.getFilterConfig().getInitParameter("services.auth");
            httpServletResponse.sendRedirect(authService + "?redirect=" + httpServletRequest.getRequestURL());
        } else{
            httpServletRequest.setAttribute("username", username);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
