package com.filestore.filestore.mapper;

import com.filestore.filestore.dto.NewUserDto;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(User user);

    User map(UserDto userDto);

    User map(NewUserDto newUserDto);
}
