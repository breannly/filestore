package com.filestore.filestore.config;

import com.filestore.filestore.config.property.StorageProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import java.net.URI;

@Configuration
public class StorageConfig {

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
}
