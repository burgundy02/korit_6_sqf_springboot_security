package com.study.SpringSecurity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 2)
public class TestAspect {

    // 흰 종이에 지점을 자를 때 지점을 자를 수 있는 위치        // ..은 0개이상 : 메개변수가 있어도되고 없어도 됨
                                                         // 접근지정자 * : 무엇이든 올 수 있다.
    @Pointcut("execution(* com.study.SpringSecurity.service.TestService.aop*(..))")
    private void pointCut() {}

    // 해당 포인트컷 위치에 핵심기능을 추가하겠다.
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        // 핵심기능 실행전에 전처리
        System.out.println("전처리"); // 부가기능
        // 이 안에서 예외 일어나 수 있다.
        // 결국 이 메소드의 실행 결과가 리턴된다. 핵심기능의 호출 aopTest메소드의 호출
        Object result = proceedingJoinPoint.proceed();  // 핵심기능 호출
        // 핵심기능 실행 후 후처리 : 핵심기능이 실행되고 나서 실행되야 하는 것들
        System.out.println("후처리"); // 부가기능

        return result;
    }
}
