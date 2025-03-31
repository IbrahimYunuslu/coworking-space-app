package com.coworking;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.SpringApplication;

@SpringBootApplication(exclude = R2dbcAutoConfiguration.class)
public class CoworkingSpaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoworkingSpaceApplication.class, args);
    }
}