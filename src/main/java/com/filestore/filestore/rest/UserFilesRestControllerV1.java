package com.filestore.filestore.rest;

import com.filestore.filestore.dto.FileDto;
import com.filestore.filestore.mapper.FileMapper;
import com.filestore.filestore.service.FileService;
import com.filestore.filestore.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{user_id}/files")
public class UserFilesRestControllerV1 {

    private final FileService fileService;
    private final FileMapper fileMapper;

    @PostMapping
    Mono<FileDto> save(@PathVariable("user_id") Long userId,
                       @RequestPart("file") FilePart filePart) {
        return FileUtils.convert(filePart)
                .flatMap(file -> fileService.save(userId, file))
                .map(fileMapper::map);
    }

    @PutMapping("/{id}")
    Mono<FileDto> update(@PathVariable("user_id") Long userId,
                         @PathVariable Long id,
                         @RequestPart("file") FilePart filePart) {
        return FileUtils.convert(filePart)
                .flatMap(file -> fileService.update(userId, id, file))
                .map(fileMapper::map);
    }

    @GetMapping("/{id}")
    Mono<FileDto> findById(@PathVariable("user_id") Long userId,
                           @PathVariable Long id) {
        return fileService.findById(userId, id)
                .map(fileMapper::map);
    }

    @DeleteMapping("/{id}")
    Mono<FileDto> delete(@PathVariable("user_id") Long userId,
                         @PathVariable Long id) {
        return fileService.delete(userId, id)
                .map(fileMapper::map);
    }
}
