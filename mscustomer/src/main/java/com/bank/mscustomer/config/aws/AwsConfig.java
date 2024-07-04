package com.bank.mscustomer.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Value("${aws.s3.access-key}")
    private String accessKeyId;

    @Value("${aws.s3.secret-key}")
    private String secretAccessKey;

    @Value("${aws.s3.sessionToken}")
    private String sessionToken;

    @Value("${aws.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken);

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
