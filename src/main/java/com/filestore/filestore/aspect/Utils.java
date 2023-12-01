package com.filestore.filestore.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;

public class Utils {

    public static Object proceed(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static boolean hasUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));
    }
}
