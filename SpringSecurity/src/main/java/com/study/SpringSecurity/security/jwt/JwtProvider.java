package com.study.SpringSecurity.security.jwt;

import com.study.SpringSecurity.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
                                                    // yml
    public JwtProvider(@Value("${jwt.secreat}") String secreat) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secreat));
    }

    public String removeBearer(String token) {
        return token.substring("Bearer ".length()); // 7
    }

    public String generateUserToken(User user){

        // date객체로 변환됨                                  // 토큰을 한 달동안 유지
        Date expireDate = new Date(new Date().getTime() + (1000l * 60 * 60 * 24 * 30));

        String token = Jwts.builder()
                .claim("userId", user.getId())
                .expiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(key)
                .build();

        return jwtParser.parseClaimsJws(token).getPayload(); // 여기서 예외 터지면 필터 중간에 터진것임
    }



}
