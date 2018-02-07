package com.gxd.sso.utils;

import com.gxd.common.utils.CookieUtils;
import com.gxd.redis.utils.RedisUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {
    private String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";
    @Autowired
    private RedisUtils redisUtils;

    public String generateToken(String signingKey, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, signingKey);

        String token = builder.compact();

        redisUtils.sSet(REDIS_SET_ACTIVE_SUBJECTS, subject);

        return token;
    }

    public String parseToken(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
        String token = CookieUtils.getValue(httpServletRequest, jwtTokenCookieName);
        if(token == null) {
            return null;
        }

        String subject = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
        if (!redisUtils.sHasKey(REDIS_SET_ACTIVE_SUBJECTS, subject)) {
            return null;
        }

        return subject;
    }

    public void invalidateRelatedTokens(HttpServletRequest httpServletRequest) {
        redisUtils.srem(REDIS_SET_ACTIVE_SUBJECTS, (String) httpServletRequest.getAttribute("username"));
    }
}

