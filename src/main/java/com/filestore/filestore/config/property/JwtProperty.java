package com.filestore.filestore.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperty {

    @Value("${app.jwt.secret}")
    private String secret;
    @Value("${app.jwt.expiration}")
    private Integer expiration;
}
