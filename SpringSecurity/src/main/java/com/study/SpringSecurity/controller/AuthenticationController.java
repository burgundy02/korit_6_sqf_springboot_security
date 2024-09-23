package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.aspect.annotation.ParamsAop;
import com.study.SpringSecurity.aspect.annotation.ValidAop;
import com.study.SpringSecurity.dto.request.ReqSigninDto;
import com.study.SpringSecurity.dto.request.ReqSignupDto;
import com.study.SpringSecurity.security.filter.JwtAccessTokenFilter;
import com.study.SpringSecurity.security.jwt.JwtProvider;
import com.study.SpringSecurity.service.SigninService;
import com.study.SpringSecurity.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Binding;
import javax.validation.Valid;

// 인증 받은 것들이 여기 옴
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private SignupService signupService;

    @Autowired
    private SigninService signinService ;



    @ValidAop   // 이게 있어서 around가 적용됨
    @ParamsAop
    @PostMapping("/signup")     // 이거 두 개 순서 상관없음(어노테이션)    BindingResult bindingResult를 만들어주는게 @Valid
    public ResponseEntity<?> signup(@Valid @RequestBody ReqSignupDto dto, BindingResult bindingResult) {
        // @Valid : 검사해서 에러가 있으면 bindingResult에 에러를 넣음. 해즈에러는 bindingResult에 에러가 있니?
        return ResponseEntity.created(null).body(signupService.signup(dto));  // 에러가 안걸리면 바로 이쪽으로 가서 회원가입 진행
    }

    @ValidAop
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody ReqSigninDto dto, BeanPropertyBindingResult bindingResult) {
        return ResponseEntity.ok().body(signinService.signin(dto));
    }



}



