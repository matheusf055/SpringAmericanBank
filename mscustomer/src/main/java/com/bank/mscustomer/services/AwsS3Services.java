package com.bank.mscustomer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class AwsS3Services {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public AwsS3Services(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadBase64Photo(String base64Photo, String customerId) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(base64Photo);

        String key = "customer_" + customerId + "_" + UUID.randomUUID() + ".png";

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(bytes));
        return key;
    }
}
