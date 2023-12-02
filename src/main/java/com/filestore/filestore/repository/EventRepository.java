package com.filestore.filestore.repository;

import com.filestore.filestore.entity.Event;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface EventRepository extends R2dbcRepository<Event, Long> {

    @Query("SELECT * FROM events WHERE user_id = :userId")
    Flux<Event> findAllByUserId(Long userId);
}
