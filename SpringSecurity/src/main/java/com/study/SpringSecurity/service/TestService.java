package com.study.SpringSecurity.service;

import com.study.SpringSecurity.aspect.annotation.Test2Aop;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String aopTest() {
        System.out.println("AOP 테스트 입니다."); // 이렇게 하면 전처리 -> AOPTEST -> 후처리로 출력할 수 있음( 마지막에 한 번 더 리턴
        return "AOP 테스트 입니다.";              // "AOP 테스트 입니다." 출력됨)

    }

    @Test2Aop   // 이거 단 얘 한테만 Test2Aop가 적용된다.
    public void aopTest2(String name, int age) {
        System.out.println("이름: " + name);
        System.out.println("나이: " + age);
        System.out.println("AOP 테스트2 입니다.");
    }

    @Test2Aop
    public void aopTest3(String phone, String address) {
        System.out.println("AOP 테스트3 입니다.");
    }



}
