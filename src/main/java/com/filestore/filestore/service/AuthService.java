package com.filestore.filestore.service;

import com.filestore.filestore.security.TokenDetails;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<TokenDetails> authenticate(String username, String password);
}
