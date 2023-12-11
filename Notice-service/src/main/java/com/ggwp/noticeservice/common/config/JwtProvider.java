package com.ggwp.noticeservice.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {
    
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String create(String email,String lolNickname, String tag, UserRole role) {
        Date expiration = Date.from(Instant.now().plus(5, ChronoUnit.HOURS));
        String jwt = Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .setSubject(email).setIssuedAt(new Date()).setExpiration(expiration)
                        .claim("lolNickname", lolNickname)
                        .claim("tag", tag)
                        .claim("role", role.getKey())
                .compact();
        return jwt;
    }

    public String validate(String jwt) {

        String email = null;
        try {

            Claims claims = Jwts.parser()
                                .setSigningKey(secretKey)
                                .parseClaimsJws(jwt)
                                .getBody();

            email = claims.getSubject();

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return email;

    }

}
