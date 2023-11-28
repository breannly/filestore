package com.filestore.filestore.rest;

import com.filestore.filestore.dto.NewUserDto;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.entity.User;
import com.filestore.filestore.mapper.UserMapper;
import com.filestore.filestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> registerUser(@RequestBody NewUserDto newUserDto) {
        User user = userMapper.map(newUserDto);
        return userService.registerUser(user)
                .map(userMapper::map);
    }
}
