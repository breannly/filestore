package com.filestore.filestore.rest;

import com.filestore.filestore.aspect.annotation.CheckBelongingToUser;
import com.filestore.filestore.dto.FileDto;
import com.filestore.filestore.mapper.FileMapper;
import com.filestore.filestore.service.FileService;
import com.filestore.filestore.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{user_id}/files")
public class UserFilesRestControllerV1 {

    private final FileService fileService;
    private final FileMapper fileMapper;

    @PostMapping
    @CheckBelongingToUser
    Mono<FileDto> save(Authentication authentication,
                       @PathVariable("user_id") Long userId,
                       @RequestPart("file") FilePart filePart) {
        return FileUtils.convert(filePart)
                .flatMap(file -> fileService.save(userId, file))
                .map(fileMapper::map);
    }

    @PutMapping("/{file_id}")
    @CheckBelongingToUser(checkFlag = true)
    Mono<FileDto> update(Authentication authentication,
                         @PathVariable("user_id") Long userId,
                         @PathVariable("file_id") Long fileId,
                         @RequestPart("file") FilePart filePart) {
        return FileUtils.convert(filePart)
                .flatMap(file -> fileService.update(userId, fileId, file))
                .map(fileMapper::map);
    }

    @GetMapping("/{file_id}")
    @CheckBelongingToUser(checkFlag = true)
    Mono<FileDto> findById(Authentication authentication,
                           @PathVariable("user_id") Long userId,
                           @PathVariable("file_id") Long fileId) {
        return fileService.findById(userId, fileId)
                .map(fileMapper::map);
    }

    @DeleteMapping("/{file_id}")
    @CheckBelongingToUser(checkFlag = true)
    Mono<FileDto> delete(Authentication authentication,
                         @PathVariable("user_id") Long userId,
                         @PathVariable("file_id") Long fileId) {
        return fileService.delete(userId, fileId)
                .map(fileMapper::map);
    }
}
