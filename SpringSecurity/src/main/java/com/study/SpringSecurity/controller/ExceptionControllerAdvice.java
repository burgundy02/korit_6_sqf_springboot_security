package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice   // IOC가 아닌 곳에서 발생한 예외는 낚아 챌 수 없다.(IOC안에서 발생한 예외만 가능)
public class ExceptionControllerAdvice {
    // 예외를 관찰하고 있다가 예외를 여기서 대신 처리해 줄 수 있다.
    @ExceptionHandler(ValidException.class)   // getMapping 등의 그런거 같은것임
    public ResponseEntity<?> validException(ValidException e) {  // 예외를 매개변수로 받아옴
        return ResponseEntity.badRequest().body(e.getFieldErrors());  // 이쪽으로 왔다는 것은 오류가 터졌다는 것.(ok절대아닌이유)
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException e) {  // 예외를 매개변수로 받아옴
        return ResponseEntity.badRequest().body(Set.of(new FieldError("authentication", "authentication", e.getMessage())));  // 이쪽으로 왔다는 것은 오류가 터졌다는 것.(ok절대아닌이유)
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException e) {  // 예외를 매개변수로 받아옴
        return ResponseEntity.badRequest().body(Set.of(new FieldError("authentication", "authentication", e.getMessage())));  // 이쪽으로 왔다는 것은 오류가 터졌다는 것.(ok절대아닌이유)
    }


}
