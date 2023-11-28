package com.filestore.filestore.repository;

import com.filestore.filestore.entity.File;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface FileRepository extends R2dbcRepository<File, Long> {

    @Query("SELECT * FROM FILES WHERE owner_id = :ownerId")
    Flux<File> findAllByOwnerId(Long ownerId);
}
