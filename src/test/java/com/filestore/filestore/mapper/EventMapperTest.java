package com.filestore.filestore.mapper;

import com.filestore.filestore.TestDataUtils;
import com.filestore.filestore.dto.EventDto;
import com.filestore.filestore.entity.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventMapperTest {

    private final EventMapper eventMapper = new EventMapperImpl();

    @Test
    public void mapTest() {
        Event event = TestDataUtils.createEvent();

        EventDto eventDto = eventMapper.map(event);

        Assertions.assertNotNull(eventDto);
        Assertions.assertEquals(event.getId(), eventDto.id());
        Assertions.assertEquals(event.getAction(), eventDto.action());
        Assertions.assertEquals(event.getStatus(), eventDto.status());
        Assertions.assertEquals(event.getCreatedAt(), eventDto.createdAt());
        Assertions.assertEquals(event.getUpdatedAt(), eventDto.updatedAt());
    }

}