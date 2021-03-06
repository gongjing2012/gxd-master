package com.gxd.sso.utils;

import com.gxd.sso.model.RedisLoginDTO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//import com.gxd.redis.utils.RedisUtils;

/**
 * @author gxd
 */
@Component
public class JwtUtils {
    private static final String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";

    //@Resource
    //private RedisUtils redisUtils;

    public String generateToken(String signingKey, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, signingKey);

        String token = builder.compact();

        //redisUtils.sadd(REDIS_SET_ACTIVE_SUBJECTS,subject);
        RedisLoginDTO redisLogin = new RedisLoginDTO(subject, token, System.currentTimeMillis() + 60L * 1000L * 30L);
        //redisUtils.setObject(subject, redisLogin, 360000000);
        return token;
    }

    public String parseToken(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
        String token = CookieUtils.getCookieValue(jwtTokenCookieName,httpServletRequest);
        if(token == null) {
            return null;
        }
        String subject = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
        //if(!redisUtils.sHasKey(REDIS_SET_ACTIVE_SUBJECTS,subject)){
        //    return null;
        //}

        return subject;
    }

    public void invalidateRelatedTokens(HttpServletRequest httpServletRequest) {
        //redisUtils.srem(REDIS_SET_ACTIVE_SUBJECTS, (String) httpServletRequest.getAttribute("username"));
    }
}

