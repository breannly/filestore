package com.filestore.filestore.aspect;

import com.filestore.filestore.aspect.annotation.CheckBelongingToUser;
import com.filestore.filestore.exception.AuthException;
import com.filestore.filestore.exception.ObjectNotFoundException;
import com.filestore.filestore.repository.FileRepository;
import com.filestore.filestore.security.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class BelongingToUserAspect {

    private final FileRepository fileRepository;

    @Before("@annotation(com.filestore.filestore.aspect.annotation.CheckBelongingToUser) && (args(authentication, userId, ..))")
    public void checkBelongingToUser(Authentication authentication, Long userId) {

        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        if (!principal.getId().equals(userId) && hasUserRole(authentication)) {
            throw new AuthException("Access denied");
        }
    }

    private boolean hasUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));
    }
}
