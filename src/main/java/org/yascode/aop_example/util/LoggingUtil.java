package org.yascode.aop_example.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class LoggingUtil {
    public static void logMethodStart(String methodFullName, Object[] args) {
        String params = Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "{", "}"));

        log.info("The method {} has started with arguments: {}", methodFullName, params);
    }

    public static void logMethodEnd(String methodFullName, long duration) {
        log.info("The method {} has ended in {} ms", methodFullName, duration);
    }

    public static void logReturningMethod(String methodFullName, Object result) {
        log.info("The method {}() returned the following result: {}", methodFullName,
                Objects.nonNull(result) ? result.toString() : null);
    }

    public static void logThrowingMethod(String methodFullName, String error) {
        log.error("Exception in {}() with cause: {}", methodFullName, error);
    }
}
