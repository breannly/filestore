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

    UserDto map(User user);

    User map(UserUpdateDto userUpdateDto);

    User map(UserNewDto userNewDto);
}
