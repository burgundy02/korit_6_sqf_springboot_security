package com.study.SpringSecurity.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TimePrintAspect {

    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.TimeAop)")
    private void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();  // 시간 제는거
        Object result = proceedingJoinPoint.proceed(); // 이거 하는데 시간 얼마나 걸리는지 / proceedingJoinPoint: 핵심기능
        stopWatch.stop();

        String infoPrint = "ClassName(" + signature.getDeclaringType().getSimpleName() + ") MethodName(" + signature.getName() + ")";
        String linePrint = "";
        for(int i = 0; i < infoPrint.length(); i++) {
            linePrint += "-";
        }

        log.info("{}", linePrint);
        log.info("{}", infoPrint);
        log.info("Time: {}초", stopWatch.getTotalTimeSeconds()); // 스탑워치 시간 젠게 얼마나 걸렸는지 알려줌(초 단위로)
        log.info("{}", linePrint);

          return result;
    }

}
