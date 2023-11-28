package com.filestore.filestore.config.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class StorageProperty {

    @Value("${app.storage.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${app.storage.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${app.storage.aws.endpoint}")
    private String endpoint;
    @Value("${app.storage.aws.region}")
    private String region;
}
