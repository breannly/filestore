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

    @GetMapping("/{event_id}")
    public Mono<EventDto> findById(@PathVariable("event_id") Long eventId) {
        return eventService.findById(eventId)
                .map(eventMapper::map);
    }

    @DeleteMapping("/{event_id}")
    public Mono<EventDto> delete(@PathVariable("event_id") Long eventId) {
        return eventService.delete(eventId)
                .map(eventMapper::map);
    }
}
