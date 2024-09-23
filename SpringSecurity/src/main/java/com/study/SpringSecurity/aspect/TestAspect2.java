package com.study.SpringSecurity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 1)  // 실행순서를 정할 수 있다.(숫자가 작을 수록 먼저 실행 됨)
public class TestAspect2 {

    // 흰 종이에 지점을 자를 때 지점을 자를 수 있는 위치        // ..은 0개이상 : 메개변수가 있어도되고 없어도 됨
                                                         // 접근지정자 * : 무엇이든 올 수 있다.
    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.Test2Aop)")
    private void pointCut() {}

    // 해당 포인트컷 위치에 핵심기능을 추가하겠다.
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        // 매개변수로 넘어온 값들을 꺼내서 쓸 수 있다. / proceedingJoinPoint안에 정보가 다 들어있어서(핵심기능)
                                                // 메소드에 대한 정보는 다 꺼내서 쓸 수 있음

            // 이러한 매개변수가 들어오면(조건) 다운캐스팅해서 꺼내서 써라
        CodeSignature signature  = (CodeSignature) proceedingJoinPoint.getSignature();
        System.out.println(signature.getName());   // 메소드명
        System.out.println(signature.getDeclaringTypeName());   // 메소드를 가지고 있는 클래스명

        Object[] args = proceedingJoinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        for(int i = 0; i < args.length; i++) {
            System.out.println(paramNames[i] + ": " + args[i]);
        }

        // 핵심기능 실행전에 전처리
        System.out.println("전처리2"); // 부가기능
        // 이 안에서 예외 일어나 수 있다.
        // 결국 이 메소드의 실행 결과가 리턴된다. 핵심기능의 호출 aopTest메소드의 호출
        Object result = proceedingJoinPoint.proceed();  // 핵심기능 호출
        // 핵심기능 실행 후 후처리 : 핵심기능이 실행되고 나서 실행되야 하는 것들
        System.out.println("후처리2"); // 부가기능

        return result;
    }
}
