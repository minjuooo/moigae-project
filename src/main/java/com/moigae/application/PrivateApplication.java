package com.moigae.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PrivateApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrivateApplication.class, args);
    }
}