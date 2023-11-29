package com.filestore.filestore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.filestore.filestore.entity.Action;
import com.filestore.filestore.entity.Status;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EventDto(Long id,
                       UserDto user,
                       FileDto file,
                       Action action,
                       Status status,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
}
