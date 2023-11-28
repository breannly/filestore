package com.filestore.filestore.service;

import com.filestore.filestore.entity.File;
import reactor.core.publisher.Mono;

public interface FileService {

    Mono<File> save(Long userId, java.io.File file);

    Mono<File> update(Long userId, Long fileId, java.io.File file);

    Mono<File> findById(Long userId, Long fileId);

    Mono<File> delete(Long userId, Long fileId);
}
