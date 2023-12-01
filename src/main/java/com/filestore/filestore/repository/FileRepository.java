package com.filestore.filestore.repository;

import com.filestore.filestore.entity.File;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FileRepository extends R2dbcRepository<File, Long> {

    @Query("SELECT * FROM files WHERE owner_id = :ownerId")
    Flux<File> findAllByOwnerId(Long ownerId);

    @Query("SELECT f.* FROM files AS f JOIN events AS e ON f.id = e.file_id WHERE e.id = :eventId")
    Mono<File> findByEventId(Long eventId);

    @Query("SELECT COUNT(*) FROM files WHERE id = :fileId AND owner_id = :userId")
    Mono<Long> existsByOwnerIdAndFileId(Long userId, Long fileId);
}
