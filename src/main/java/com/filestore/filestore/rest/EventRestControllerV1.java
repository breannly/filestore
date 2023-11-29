package com.filestore.filestore.rest;

import com.filestore.filestore.dto.EventDto;
import com.filestore.filestore.entity.Event;
import com.filestore.filestore.mapper.EventMapper;
import com.filestore.filestore.repository.EventRepository;
import com.filestore.filestore.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventRestControllerV1 {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping("/{id}")
    public Mono<EventDto> findById(@PathVariable Long id) {
        return eventService.findById(id)
                .map(eventMapper::map);
    }

    @DeleteMapping("/{id}")
    public Mono<EventDto> delete(@PathVariable Long id) {
        return eventService.delete(id)
                .map(eventMapper::map);
    }
}
