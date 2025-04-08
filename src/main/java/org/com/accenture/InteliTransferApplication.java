package org.com.accenture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InteliTransferApplication {
    public static void main(String[] args) {
        SpringApplication.run(InteliTransferApplication.class, args);
    }
} 