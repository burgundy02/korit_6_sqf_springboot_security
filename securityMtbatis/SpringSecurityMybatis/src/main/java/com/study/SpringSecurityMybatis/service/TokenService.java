package com.study.SpringSecurityMybatis.service;

import com.study.SpringSecurityMybatis.entity.User;
import com.study.SpringSecurityMybatis.exception.AccessTokenValidException;
import com.study.SpringSecurityMybatis.repository.UserMapper;
import com.study.SpringSecurityMybatis.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProvider jwtProvider;

    public Boolean isValidAccessToken(String bearerAccessToken) {
        try {
            String accessToken = jwtProvider.removeBearer(bearerAccessToken);
            Claims claims = jwtProvider.getClaims(accessToken);
            System.out.println("test");
            Long userId = ((Integer) claims.get("userId")).longValue();
            System.out.println(userId);
            User user = userMapper.findById(userId);
            System.out.println(user);

            if(user == null) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new AccessTokenValidException("AccessToken 유효성 검사 실패");
        }
        return true;
    }


}
