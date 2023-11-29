package com.filestore.filestore.service;

import com.filestore.filestore.entity.Event;
import reactor.core.publisher.Mono;

public interface EventService {

    Mono<Event> findById(Long id);

    Mono<Event> delete(Long id);
}
