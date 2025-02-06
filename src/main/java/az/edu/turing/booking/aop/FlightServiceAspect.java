package az.edu.turing.booking.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FlightServiceAspect {

    @Around("execution(* az.edu.turing.booking.service.FlightService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        log.info("Method: {} | Execution time: {} ms", joinPoint.getSignature().getName(), duration);
        return result;
    }

    @Before("execution(* az.edu.turing.booking.service.FlightService.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Method called: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "execution(* az.edu.turing.booking.service.FlightService.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method successfully completed: {} | Result: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(value = "execution(* az.edu.turing.booking.service.FlightService.*(..))", throwing = "ex")
    public void logAfterException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in method: {} | Error: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}

