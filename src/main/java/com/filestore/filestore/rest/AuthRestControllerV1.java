package com.filestore.filestore.rest;

import com.filestore.filestore.dto.TokenDto;
import com.filestore.filestore.dto.UserNewDto;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.entity.User;
import com.filestore.filestore.mapper.UserMapper;
import com.filestore.filestore.service.AuthService;
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
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserNewDto userNewDto) {
        User user = userMapper.map(userNewDto);
        return userService.register(user)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<TokenDto> login(@RequestBody UserNewDto userNewDto) {
        return authService.authenticate(userNewDto.username(), userNewDto.password())
                .map(token -> new TokenDto(
                        token.getUserId(),
                        token.getToken(),
                        token.getIssuedAt(),
                        token.getExpiresAt())
                );
    }
}
