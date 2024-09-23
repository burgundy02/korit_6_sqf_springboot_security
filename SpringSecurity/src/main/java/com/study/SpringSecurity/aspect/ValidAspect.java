package com.study.SpringSecurity.aspect;

import com.study.SpringSecurity.dto.request.ReqSignupDto;
import com.study.SpringSecurity.exception.ValidException;
import com.study.SpringSecurity.service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

@Slf4j
@Aspect
@Component
public class ValidAspect {

    @Autowired
    private SignupService signupService;

    // ValidAop어노테이션이 달린 얘가 핵심기능이다.(어노테이션 달린 얘 통채로가 ProceedingJoinPoint이다.)
    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.ValidAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //이 배열에 dto, bindingResult 매개변수가 담겨 있다.
        Object[] args = proceedingJoinPoint.getArgs();     // 핵심기능이니까 signup메소드, .getArgs() 매개변수를 들고옴
        //BeanProperty: 인터페이스가 구헌된 것임
        BeanPropertyBindingResult bindingResult = null;
        // args이 배열안에 BeanPropertyBindingResult타입이 있으면 그걸 다운캐스팅해서 bindingResult변수에 담아라
        for(Object arg : args) {
            if(arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }

        switch (proceedingJoinPoint.getSignature().getName()) {
            case "signup" :
                validSignupDto(args, bindingResult);
                break;
        }

        if(bindingResult.hasErrors()) {
            // 예외를 생성해서 던진다(주소를 생성해서) 그리고 그걸 받는게 @ExceptionHandler
            throw new ValidException("유효성 검사 오류", bindingResult.getFieldErrors()); // 오류났던
        }

        return proceedingJoinPoint.proceed();
    }

    // 외부에서 쓸 일 없으니 private
    private void validSignupDto(Object[] args, BeanPropertyBindingResult bindingResult) {
        for(Object arg : args) {
            if(arg.getClass() == ReqSignupDto.class) {
                ReqSignupDto dto = (ReqSignupDto) arg;
                // 패스워드와 체크패스워드가 다르면 회원가입못하게 해야 돼서
                if(!dto.getPassword().equals(dto.getCheckPassword())) {
                    FieldError fieldError = new FieldError("checkPassword", "checkPassword", "비밀번호가 일치하지 않습니다.");
                    bindingResult.addError(fieldError);  // valide가 만들어놓은 bindingResult에 넣음
                }
                if(signupService.isDuplicatedUsername(dto.getUsername())) {
                    FieldError fieldError = new FieldError("username", "username", "이미 존재하는 사용자이름입니다.");
                    bindingResult.addError(fieldError);
                }
                break;
            }
        }
    }

}
