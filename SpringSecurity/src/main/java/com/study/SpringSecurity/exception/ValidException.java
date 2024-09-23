package com.study.SpringSecurity.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

// 예외받기
public class ValidException extends RuntimeException{

    @Getter                  // 멤버변수
    private List<FieldError> fieldErrors;

    // 예외 터졌을 때 뜰 메시지
    public ValidException(String message, List<FieldError> fieldErrors) {
        super(message); // 예외 터졌을 때 메시지가 들어있어야 출력이 됨
        this.fieldErrors = fieldErrors;
    }

}
