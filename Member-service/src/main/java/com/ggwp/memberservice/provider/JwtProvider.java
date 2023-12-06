package com.ggwp.memberservice.provider;

import com.ggwp.memberservice.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    public String create(String uuid, String lolNickname, String tag, UserRole role) {
        logger.info("Creating JWT token for uuid: {}", uuid);
        Date expiration = Date.from(Instant.now().plus(5, ChronoUnit.HOURS));
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(uuid)
                .setIssuedAt(new Date()).setExpiration(expiration)
                .claim("lolNickname", lolNickname)
                .claim("tag", tag)
                .claim("role", role.getKey())
                .compact();
        logger.info("JWT token created: {}", jwt);
        return jwt;
    }

    public String validate(String jwt) {
        logger.info("Validating JWT token");
        String uuid = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
            uuid = claims.getSubject();
            logger.info("JWT token validated. UUID: {}", uuid);
        } catch (Exception exception) {
            logger.error("JWT token validation failed", exception);
            return null;
        }
        return uuid;
    }
}
