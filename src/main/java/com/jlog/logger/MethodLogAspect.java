package com.jlog.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLogAspect {

    private static final Logger log = LoggerFactory.getLogger(MethodLogAspect.class);

    @Around("execution(* com.jlog..*(..)) && " +
            "!within(com.jlog.logger.*) && " +
            "!within(com.jlog.exception.*)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!log.isInfoEnabled()) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long executionTimeMillis = endTime - startTime;

        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        log.info("{}.{}() {}ms", className, methodName, executionTimeMillis);

        return result;
    }
}
