package com.moigae.application.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * component 패키지의 하위 패키지 + 모든 메서드에 적용
     * <p>
     * 예외 상세 정보를 로그로 남긴다.
     * <p>
     * 1. 예러 발생 메서드
     * 2. 메서드 실행에 사용되는 매개변수
     * 3. 에러 정보
     */
    @Around("execution(* com.moigae.application.component..*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        try {
            result = pjp.proceed();
        } catch (Exception e) {
            logger.error("Error occurred in method: " + pjp.getSignature().getName());
            logger.error("Arguments: " + Arrays.toString(pjp.getArgs()));
            logger.error("Error Details: ", e);
            throw e;
        }
        return result;
    }
}