package com.example.jwt.jwt;

import com.example.jwt.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private static final long TOKEN_VALID_TIME = 24 * 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_VALID_TIME = 30 * 24 * 60 * 60 * 1000L;

    public String createToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("id", member.getId());
        claims.put("username", member.getUsername());

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                .signWith(
                        Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            return !Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(removeBearer(token))
                    .getBody()
                    .getExpiration().before(new Date());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("토큰 검증 실패");
        }
    }

    public MemberInfo getClaim(String token) {
        Claims claimsBody = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(removeBearer(token))
                .getBody();

        return MemberInfo.builder()
                .id(Long.valueOf((Integer) claimsBody.getOrDefault("id", 0L)))
                .username(claimsBody.getOrDefault("username", "").toString())
                .build();
    }

    private String removeBearer(String token) {
        return token.replace("Bearer", "").trim();
    }
}