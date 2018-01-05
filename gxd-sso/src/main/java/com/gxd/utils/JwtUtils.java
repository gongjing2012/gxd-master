package com.gxd.utils;

import com.gxd.redis.config.RedisService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author gxd
 */
@Component
public class JwtUtils {
    private static final String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";

    @Resource
    private RedisService redisService;

    public String generateToken(String signingKey, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, signingKey);

        String token = builder.compact();

        redisService.sadd(REDIS_SET_ACTIVE_SUBJECTS,subject);
        return token;
    }

    public String parseToken(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
        String token = CookieUtils.getCookieValue(jwtTokenCookieName,httpServletRequest);
        if(token == null) {
            return null;
        }

        String subject = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
        if(!redisService.sHasKey(REDIS_SET_ACTIVE_SUBJECTS,subject)){
            return null;
        }

        return subject;
    }

    public void invalidateRelatedTokens(HttpServletRequest httpServletRequest) {
        redisService.srem(REDIS_SET_ACTIVE_SUBJECTS, (String) httpServletRequest.getAttribute("username"));
    }
}

