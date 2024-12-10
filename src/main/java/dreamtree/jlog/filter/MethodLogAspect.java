package dreamtree.jlog.filter;

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

    @Around("execution(* dreamtree.jlog..*(..)) && " +
            "!within(dreamtree.jlog.filter.*) && " +
            "!within(dreamtree.jlog.exception.*)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!log.isInfoEnabled()) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long executionTimeMillis = endTime - startTime;

        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        log.info("{}.{}() {}ms", className, methodName, executionTimeMillis);

        return result;
    }
}
