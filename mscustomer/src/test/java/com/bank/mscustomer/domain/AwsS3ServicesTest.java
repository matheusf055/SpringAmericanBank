package com.bank.mscustomer.domain;

import com.bank.mscustomer.services.AwsS3Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AwsS3ServicesTest {

    private static final String BUCKET_NAME = "american-bank-bucket";

    private AwsS3Services awsS3Services;

    @Mock
    private S3Client s3Client;

    @BeforeEach
    public void setup() {
        awsS3Services = new AwsS3Services(s3Client);
    }

    @Test
    public void testUploadBase64Photo() throws IOException {
        String base64Photo = "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"; // Substitua pela sua foto codificada em base64
        String customerId = "1";

        try {
            byte[] bytes = Base64.getDecoder().decode(base64Photo);
            UUID uuid = UUID.randomUUID();
            String key = "customer_" + customerId + "_" + uuid + ".png";

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .build();

            PutObjectResponse response = PutObjectResponse.builder()
                    .build();

            when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                    .thenReturn(response);

            String resultKey = awsS3Services.uploadBase64Photo(base64Photo, customerId);

            assertTrue(resultKey.startsWith("customer_" + customerId + "_"));
            assertTrue(resultKey.endsWith(".png"));

        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao decodificar Base64: " + e.getMessage());
            throw e;
        }
    }
}
