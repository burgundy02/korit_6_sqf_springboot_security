package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.security.principal.PrincsipalUser;
import com.study.SpringSecurity.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 요청: params, requestbody / params : get요청에다가 씀 (물음표 뒤에 키, 벨류 있는 거)
 *
 */

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")     // get요청은 params로 받는다.
    public ResponseEntity<?> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincsipalUser princsipalUser = (PrincsipalUser) authentication.getPrincipal();
        return ResponseEntity.ok(princsipalUser);
    }

}
