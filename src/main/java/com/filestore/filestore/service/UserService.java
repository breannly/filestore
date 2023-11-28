package com.filestore.filestore.service;

import com.filestore.filestore.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> register(User user);

    Mono<User> update(Long id, User user);

    Mono<User> findById(Long id);

    Mono<User> delete(Long id);
}
