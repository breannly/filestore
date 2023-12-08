package com.filestore.filestore.service.impl;

import com.filestore.filestore.entity.*;
import com.filestore.filestore.exception.ObjectNotFoundException;
import com.filestore.filestore.repository.EventRepository;
import com.filestore.filestore.repository.FileRepository;
import com.filestore.filestore.repository.UserRepository;
import com.filestore.filestore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final EventRepository eventRepository;

    @Override
    public Mono<User> register(User user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(u -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(Mono.defer(() -> userRepository.save(
                        user.toBuilder()
                                .password(passwordEncoder.encode(user.getPassword()))
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
                                        .password(passwordEncoder.encode(user.getPassword()))
                                        .updatedAt(LocalDateTime.now())
                                        .build()
                        )
                ).switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")))
                .doOnSuccess(u -> log.info("IN updateUser - user: {} updated", u));

    }

    @Override
    public Mono<User> findById(Long id) {
        return Mono.zip(userRepository.findById(id),
                        eventRepository.findAllByUserId(id).collectList(),
                        fileRepository.findAllByUserId(id).collectMap(File::getId, file -> file))
                .map(tuples -> {
                    User user = tuples.getT1();
                    List<Event> events = tuples.getT2();
                    Map<Long, File> files = tuples.getT3();
                    events.forEach(event -> event.setFile(files.get(event.getFileId())));
                    user.setEvents(events);
                    return user;
                }).switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")))
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
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")))
                .doOnSuccess(u -> log.info("IN deletedUser - user: {} deleted", u));
    }
}
