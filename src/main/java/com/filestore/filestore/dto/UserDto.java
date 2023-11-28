package com.filestore.filestore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.filestore.filestore.entity.UserRole;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserDto(Long id,
                      String username,
                      String password,
                      UserRole role,
                      Date createdAt,
                      Date updatedAt) {
}
