package com.filestore.filestore.service.impl;

import com.filestore.filestore.entity.Event;
import com.filestore.filestore.entity.File;
import com.filestore.filestore.entity.Status;
import com.filestore.filestore.entity.User;
import com.filestore.filestore.exception.ObjectNotFoundException;
import com.filestore.filestore.repository.EventRepository;
import com.filestore.filestore.repository.FileRepository;
import com.filestore.filestore.repository.UserRepository;
import com.filestore.filestore.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Override
    public Mono<Event> findById(Long id) {
        return eventRepository.findById(id)
                .flatMap(event -> Mono.zip(userRepository.findById(event.getUserId()),
                                           fileRepository.findById(event.getFileId()))
                        .map(tuples -> {
                            User user = tuples.getT1();
                            File file = tuples.getT2();
                            event.setUser(user);
                            event.setFile(file);
                            return event;
                        }))
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Event not found")));
    }

    @Override
    public Mono<Event> delete(Long id) {
        return eventRepository.findById(id)
                .flatMap(event -> eventRepository.save(
                        event.toBuilder()
                                .status(Status.DELETED)
                                .updatedAt(LocalDateTime.now())
                                .build()))
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Event not found")));
    }
}
