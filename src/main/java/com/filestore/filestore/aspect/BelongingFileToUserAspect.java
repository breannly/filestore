package com.filestore.filestore.aspect;

import com.filestore.filestore.aspect.annotation.CheckBelongingToUser;
import com.filestore.filestore.exception.AuthException;
import com.filestore.filestore.repository.FileRepository;
import com.filestore.filestore.security.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Order(1)
@Aspect
@Component
@RequiredArgsConstructor
public class BelongingFileToUserAspect {

    private final FileRepository fileRepository;

    @Around("@annotation(com.filestore.filestore.aspect.annotation.CheckBelongingToUser) && args(authentication, userId, fileId, ..))")
    public Object checkBelongingToUser(ProceedingJoinPoint proceedingJoinPoint, Authentication authentication, Long userId, Long fileId) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        CheckBelongingToUser annotation = signature.getMethod().getAnnotation(CheckBelongingToUser.class);

        if (annotation != null && annotation.checkFlag() && Utils.hasUserRole(authentication)) {
            return fileRepository.countByOwnerIdAndFileId(userId, fileId)
                    .flatMap(count -> {
                        if (count == 0) {
                            return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN));
                        }
                        return (Mono<?>) Utils.proceed(proceedingJoinPoint);
                    });
        }

        return Utils.proceed(proceedingJoinPoint);
    }
}
