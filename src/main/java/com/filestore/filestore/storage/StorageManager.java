package com.filestore.filestore.storage;

import reactor.core.publisher.Mono;

import java.io.File;

public interface StorageManager {

    Mono<String> upload(String path, File file);
}
