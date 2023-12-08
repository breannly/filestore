package com.filestore.filestore.repository;

import com.filestore.filestore.entity.File;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface FileRepository extends R2dbcRepository<File, Long> {

    @Query("SELECT * FROM files AS f JOIN events AS e ON f.id = e.file_id JOIN users AS u ON e.user_id = u.id WHERE u.id = :userId")
    Flux<File> findAllByUserId(Long userId);
}
