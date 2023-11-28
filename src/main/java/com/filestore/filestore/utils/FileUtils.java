package com.filestore.filestore.utils;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static Mono<File> convert(FilePart filePart) {
        String filename = filePart.filename();
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), filename);
        File file = path.toFile();

        return filePart.transferTo(file).thenReturn(file);
    }

    public static String generatePath(Long userId, String fileName) {
        return String.format("%s/%d_%s", userId, System.currentTimeMillis(), fileName);
    }
}
