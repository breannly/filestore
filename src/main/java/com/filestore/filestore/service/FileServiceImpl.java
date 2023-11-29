package com.filestore.filestore.service;

import com.filestore.filestore.entity.Action;
import com.filestore.filestore.entity.Event;
import com.filestore.filestore.entity.File;
import com.filestore.filestore.entity.Status;
import com.filestore.filestore.exception.ObjectNotFoundException;
import com.filestore.filestore.repository.EventRepository;
import com.filestore.filestore.repository.FileRepository;
import com.filestore.filestore.repository.UserRepository;
import com.filestore.filestore.storage.StorageManager;
import com.filestore.filestore.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final StorageManager storageManager;
    private final FileRepository fileRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<File> save(Long userId, java.io.File file) {
        String fileName = file.getName();
        return userRepository.findById(userId)
                .flatMap(user -> {
                    String path = FileUtils.generatePath(userId, fileName);
                    return storageManager.upload(path, file)
                            .doOnSuccess(f -> log.info("In saveFile - file: {} uploaded", fileName))
                            .flatMap(url -> fileRepository.save(
                                    File.builder()
                                            .owner(user)
                                            .fileName(fileName)
                                            .filePath(url)
                                            .status(Status.ACTIVE)
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build()))
                            .doOnSuccess(f -> eventRepository.save(
                                    Event.builder()
                                            .user(user)
                                            .file(f)
                                            .action(Action.CREATE)
                                            .status(Status.ACTIVE)
                                            .createdAt(LocalDateTime.now())
                                            .updatedAt(LocalDateTime.now())
                                            .build())
                                    .doOnSuccess(e -> log.info("In saveFile - event: {} saved", e))
                                    .subscribe()
                            )
                            .doOnSuccess(f -> log.info("In saveFile - file: {} saved", fileName));
                })
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")));
    }

    @Override
    public Mono<File> update(Long userId, Long fileId, java.io.File file) {
        String fileName = file.getName();
        return userRepository.findById(userId)
                .flatMap(user -> fileRepository.findById(fileId)
                        .flatMap(foundFile -> {
                            String path = FileUtils.generatePath(userId, fileName);
                            return storageManager.upload(path, file)
                                    .doOnSuccess(f -> log.info("In updateFile - file: {} uploaded", fileName))
                                    .flatMap(url -> fileRepository.save(
                                                    foundFile.toBuilder()
                                                            .fileName(fileName)
                                                            .filePath(url)
                                                            .updatedAt(LocalDateTime.now())
                                                            .build())
                                            .doOnSuccess(f -> log.info("In saveFile - file: {} saved", fileName)));
                        })
                        .switchIfEmpty(Mono.error(new ObjectNotFoundException("File not found")))
                )
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")));
    }

    @Override
    public Mono<File> findById(Long userId, Long fileId) {
        return userRepository.findById(userId)
                .flatMap(user -> fileRepository.findById(fileId)
                        .switchIfEmpty(Mono.error(new ObjectNotFoundException("File not found")))
                        .map(file -> {
                            file.setOwner(user);
                            return file;
                        })
                        .doOnSuccess(f -> log.info("In getByIdFile - file: {} found", f)))
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")));
    }

    @Override
    public Mono<File> delete(Long userId, Long fileId) {
        return userRepository.findById(userId)
                .flatMap(user -> fileRepository.findById(fileId)
                        .flatMap(file -> fileRepository.save(
                                file.toBuilder()
                                        .status(Status.DELETED)
                                        .updatedAt(LocalDateTime.now())
                                        .build()))
                        .switchIfEmpty(Mono.error(new ObjectNotFoundException("File not found")))
                        .doOnSuccess(f -> log.info("In getByIdFile - file: {} found", f)))
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")));
    }
}