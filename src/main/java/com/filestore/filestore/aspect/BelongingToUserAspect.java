package com.filestore.filestore.aspect;

import com.filestore.filestore.security.CustomPrincipal;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Order(0)
@Component
public class BelongingToUserAspect {

    @Before("@annotation(com.filestore.filestore.aspect.annotation.CheckBelongingToUser) && args(authentication, userId, ..)")
    public void checkBelongingToUser(Authentication authentication, Long userId) {

        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        if (!principal.getId().equals(userId) && hasUserRole(authentication)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public static boolean hasUserRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));
    }
}
