package com.filestore.filestore.service;

import com.filestore.filestore.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> registerUser(User user);
}
