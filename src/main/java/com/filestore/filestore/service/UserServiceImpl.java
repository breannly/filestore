package com.filestore.filestore.service;

import com.filestore.filestore.entity.Status;
import com.filestore.filestore.entity.UserRole;
import com.filestore.filestore.entity.User;
import com.filestore.filestore.exception.ObjectNotFoundException;
import com.filestore.filestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> register(User user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(u -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(Mono.defer(() -> userRepository.save(
                        user.toBuilder()
                                .role(UserRole.USER)
                                .status(Status.ACTIVE)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build()
                )))
                .map(o -> (User) o)
                .doOnSuccess(u -> {
                    log.info("IN registerUser - user: {} created", u);
                });
    }

    @Override
    public Mono<User> update(Long id, User user) {
        return userRepository.findById(id)
                .flatMap(u -> userRepository.save(
                                u.toBuilder()
                                        .password(user.getPassword())
                                        .updatedAt(LocalDateTime.now())
                                        .build()
                        )
                ).switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")))
                .doOnSuccess(u -> log.info("IN updateUser - user: {} updated", u));

    }

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")))
                .doOnSuccess(u -> log.info("IN findByIdUser - user: {} found", u));
    }

    @Override
    public Mono<User> delete(Long id) {
        return userRepository.findById(id)
                .flatMap(u -> userRepository.save(
                        u.toBuilder()
                                .status(Status.DELETED)
                                .build()
                ))
                .doOnSuccess(u -> log.info("IN deletedUser - user: {} deleted", u));
    }
}
