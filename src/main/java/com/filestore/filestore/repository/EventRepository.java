package com.filestore.filestore.repository;

import com.filestore.filestore.entity.Event;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface EventRepository extends R2dbcRepository<Event, Long> {
}
