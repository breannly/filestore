package com.filestore.filestore.mapper;

import com.filestore.filestore.dto.EventDto;
import com.filestore.filestore.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    EventDto map(Event event);

    Event map(EventDto event);
}
