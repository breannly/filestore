package com.filestore.filestore.rest;

import com.filestore.filestore.aspect.annotation.CheckBelongingToUser;
import com.filestore.filestore.dto.UserUpdateDto;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.entity.User;
import com.filestore.filestore.mapper.UserMapper;
import com.filestore.filestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;

    @PutMapping("/{user_id}")
    @CheckBelongingToUser
    public Mono<UserDto> update(Authentication authentication,
                                @PathVariable("user_id") Long userId,
                                @RequestBody UserUpdateDto userUpdateDto) {
        User user = userMapper.map(userUpdateDto);
        return userService.update(userId, user)
                .map(userMapper::map);
    }

    @GetMapping("/{user_id}")
    @CheckBelongingToUser
    public Mono<UserDto> findById(Authentication authentication,
                                  @PathVariable("user_id") Long userId) {
        return userService.findById(userId)
                .map(userMapper::map);
    }

    @DeleteMapping("/{user_id}")
    @CheckBelongingToUser
    public Mono<UserDto> delete(Authentication authentication,
                                @PathVariable("user_id") Long userId) {
        return userService.delete(userId)
                .map(userMapper::map);
    }
}
