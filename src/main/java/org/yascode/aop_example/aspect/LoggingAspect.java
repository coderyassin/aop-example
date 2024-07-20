package org.yascode.aop_example.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.yascode.aop_example.util.LoggingUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private Map<String, Long> startTimeMap = new HashMap<>();

    @Pointcut("execution(* org.yascode.aop_example.controller.*.*(..))")
    private void controllerMethods() {}

    @Before("controllerMethods()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest httpServletRequest = (HttpServletRequest) args[args.length - 1];
        log.info("Received request: {} {} from {}",
                httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(),
                httpServletRequest.getRemoteAddr());
        String methodFullName = getMethodFullName(joinPoint);
        startTimeMap.put(methodFullName, System.currentTimeMillis());
        LoggingUtil.logMethodStart(methodFullName, Arrays.copyOf(args, args.length - 1));
    }

    @After("controllerMethods()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        String methodFullName = getMethodFullName(joinPoint);
        Long startTime = startTimeMap.remove(methodFullName);
        if(Objects.nonNull(startTime)) {
            long duration = System.currentTimeMillis() - startTime;
            LoggingUtil.logMethodEnd(methodFullName, duration);
        }
    }

    @Pointcut("execution(* org.yascode.aop_example.service.*.*(..))")
    private void serviceMethods(){}

    @Before("serviceMethods()")
    public void beforeAdvice(JoinPoint joinPoint){
        String methodFullName = getMethodFullName(joinPoint);
        startTimeMap.put(methodFullName, System.currentTimeMillis());
        LoggingUtil.logMethodStart(methodFullName, joinPoint.getArgs());
    }

    @After("serviceMethods()")
    public void afterAdvice(JoinPoint joinPoint){
        String methodFullName = getMethodFullName(joinPoint);
        Long startTime = startTimeMap.remove(methodFullName);
        if(Objects.nonNull(startTime)) {
            long duration = System.currentTimeMillis() - startTime;
            LoggingUtil.logMethodEnd(methodFullName, duration);
        }
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void afterThrowingServiceMethod(JoinPoint joinPoint, Throwable error) {
        log.error("Exception in {}() with cause = {}", getMethodFullName(joinPoint),
                Objects.nonNull(error.getMessage()) ? error.getMessage() : "NULL");
    }

    private String getMethodFullName(JoinPoint joinPoint) {
        return new StringBuilder()
                .append(joinPoint.getSignature().getDeclaringTypeName())
                .append(".")
                .append(joinPoint.getSignature().getName())
                .toString();
    }
}
