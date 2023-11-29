package com.filestore.filestore.repository;

import com.filestore.filestore.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {

    Mono<User> findByUsername (String username);

    @Query("SELECT u.* FROM users AS u JOIN events AS e ON u.id = e.user_id WHERE e.id = :eventId")
    Mono<User> findByEventId(Long eventId);
}
