package com.filestore.filestore.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class AwsStorageManagerImpl implements StorageManager {

    private final S3AsyncClient s3Client;

    @Value("${app.storage.aws.bucketName}")
    private String bucketName;

    @Override
    public Mono<String> upload(String path, File file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        return Mono.fromFuture(() -> s3Client.putObject(request, AsyncRequestBody.fromFile(file)))
                .switchIfEmpty(Mono.error(new RuntimeException("Failed to upload file to S3")))
                .map(response -> s3Client.utilities().getUrl(GetUrlRequest.builder().key(path).bucket(bucketName).build()))
                .map(URL::toString);
    }
}
