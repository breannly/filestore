package com.filestore.filestore.config;

import com.filestore.filestore.config.property.JwtProperty;
import com.filestore.filestore.config.property.StorageProperty;
import com.filestore.filestore.converter.EventWriteConverter;
import com.filestore.filestore.repository.UserRepository;
import com.filestore.filestore.service.AuthService;
import com.filestore.filestore.service.impl.AuthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public AuthService authService(PasswordEncoder passwordEncoder,
                                   UserRepository userRepository,
                                   JwtProperty jwtProperty) {
        return new AuthServiceImpl(passwordEncoder,
                                   userRepository,
                                   jwtProperty.getSecret(),
                                   jwtProperty.getExpiration());
    }

    @Bean
    public S3AsyncClient s3AsyncClient(StorageProperty storageProperty) {
        AwsCredentials credentials = AwsBasicCredentials.create(storageProperty.getAccessKey(), storageProperty.getSecretKey());
        return S3AsyncClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(storageProperty.getRegion()))
                .endpointOverride(URI.create(storageProperty.getEndpoint()))
                .httpClientBuilder(NettyNioAsyncHttpClient.builder())
                .build();
    }

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new EventWriteConverter());
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, converters);
    }
}
