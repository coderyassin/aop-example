package org.yascode.aop_example.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yascode.aop_example.util.LoggingUtil;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private Map<String, Long> startTimeMap = new HashMap<>();

    @Pointcut("execution(* org.yascode.aop_example.controller.*.*(..))")
    private void controllerMethods() {
    }

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
        if (Objects.nonNull(startTime)) {
            long duration = System.currentTimeMillis() - startTime;
            LoggingUtil.logMethodEnd(methodFullName, duration);
        }
    }

    @Around("controllerMethods()")
    public Object aroundControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("------------------------------------------------------------------------------------------");
        Map<String, String> signature = getSignature(proceedingJoinPoint);
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(signature.get("returnType") + " ");
        signatureBuilder.append(signature.get("methodName"));
        signatureBuilder.append("(" + signature.get("parameterNames") + ")");
        log.info("A call to the API: {}", signatureBuilder);
        Object proceed = proceedingJoinPoint.proceed();
        log.info("------------------------------------------------------------------------------------------");
        return proceed;
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void afterReturningControllerMethod(JoinPoint joinPoint, Object result) {
        if (result instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity) result;
            HttpStatusCode statusCode = responseEntity.getStatusCode();
            Object body = responseEntity.getBody();
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("statusCode", statusCode.value());
            responseMap.put("body", body);
            LoggingUtil.logReturningMethod(getMethodFullName(joinPoint), responseMap);
        } else {
            LoggingUtil.logReturningMethod(getMethodFullName(joinPoint),
                    Objects.nonNull(result) ? result.toString() : null);
        }
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "error")
    public void afterThrowingControllerMethod(JoinPoint joinPoint, Throwable error) {
        LoggingUtil.logThrowingMethod(getMethodFullName(joinPoint),
                Objects.nonNull(error) ? error.getMessage() : null);
    }

    @Pointcut("execution(* org.yascode.aop_example.service.*.*(..))")
    private void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        String methodFullName = getMethodFullName(joinPoint);
        startTimeMap.put(methodFullName, System.currentTimeMillis());
        LoggingUtil.logMethodStart(methodFullName, joinPoint.getArgs());
    }

    @After("serviceMethods()")
    public void afterServiceMethod(JoinPoint joinPoint) {
        String methodFullName = getMethodFullName(joinPoint);
        Long startTime = startTimeMap.remove(methodFullName);
        if (Objects.nonNull(startTime)) {
            long duration = System.currentTimeMillis() - startTime;
            LoggingUtil.logMethodEnd(methodFullName, duration);
        }
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterReturningServiceMethod(JoinPoint joinPoint, Object result) {
        LoggingUtil.logReturningMethod(getMethodFullName(joinPoint),
                Objects.nonNull(result) ? result.toString() : null);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void afterThrowingServiceMethod(JoinPoint joinPoint, Throwable error) {
        LoggingUtil.logThrowingMethod(getMethodFullName(joinPoint),
                Objects.nonNull(error) ? error.getMessage() : null);
    }

    @Pointcut("execution(* org.yascode.aop_example.repository.*.*(..))")
    private void repositoryMethods() {
    }

    @Before("repositoryMethods()")
    public void beforeRepositoryMethod(JoinPoint joinPoint) {
        String methodFullName = getMethodFullName(joinPoint);
        startTimeMap.put(methodFullName, System.currentTimeMillis());
        LoggingUtil.logMethodStart(methodFullName, joinPoint.getArgs());
    }

    @After("repositoryMethods()")
    public void afterRepositoryMethod(JoinPoint joinPoint) {
        String methodFullName = getMethodFullName(joinPoint);
        Long startTime = startTimeMap.remove(methodFullName);
        if (Objects.nonNull(startTime)) {
            long duration = System.currentTimeMillis() - startTime;
            LoggingUtil.logMethodEnd(methodFullName, duration);
        }
    }

    @AfterThrowing(pointcut = "repositoryMethods()", throwing = "error")
    public void afterThrowingRepositoryMethod(JoinPoint joinPoint, Throwable error) {
        LoggingUtil.logThrowingMethod(getMethodFullName(joinPoint),
                Objects.nonNull(error) ? error.getMessage() : null);
    }

    private Map<String, String> getSignature(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String returnType = signature.getReturnType().getName();
        String declaringTypeName = signature.getDeclaringTypeName();
        String name = signature.getMethod().getName();
        String parameterNames = Arrays.stream(signature.getParameterNames())
                .collect(Collectors.joining(", "));

        return Map.of("returnType", returnType,
                "methodName", declaringTypeName + "." + name,
                "parameterNames", parameterNames);
    }

    private String getMethodFullName(JoinPoint joinPoint) {
        return new StringBuilder()
                .append(joinPoint.getSignature().getDeclaringTypeName())
                .append(".")
                .append(joinPoint.getSignature().getName())
                .toString();
    }

}
