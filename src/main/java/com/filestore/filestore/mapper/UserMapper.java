package com.filestore.filestore.mapper;

import com.filestore.filestore.dto.UserNewDto;
import com.filestore.filestore.dto.UserUpdateDto;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "status", source = "status")
    UserDto map(User user);

    @Mapping(target = "password", source = "password")
    User map(UserUpdateDto userUpdateDto);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    User map(UserNewDto userNewDto);
}
