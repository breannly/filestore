package com.filestore.filestore.rest;

import com.filestore.filestore.dto.UserUpdateDto;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.entity.User;
import com.filestore.filestore.mapper.UserMapper;
import com.filestore.filestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/{id}")
    public Mono<UserDto> update(@PathVariable Long id,
                                @RequestBody UserUpdateDto userUpdateDto) {
        User user = userMapper.map(userUpdateDto);
        return userService.update(id, user)
                .map(userMapper::map);
    }

    @GetMapping("/{id}")
    public Mono<UserDto> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(userMapper::map);
    }

    @DeleteMapping("/{id}")
    public Mono<UserDto> delete(@PathVariable Long id) {
        return userService.delete(id)
                .map(userMapper::map);
    }
}
