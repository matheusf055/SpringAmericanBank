package com.bank.mspayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MspaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MspaymentApplication.class, args);
    }

}
