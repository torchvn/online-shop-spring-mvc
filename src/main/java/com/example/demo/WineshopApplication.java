package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableScheduling
public class WineshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(WineshopApplication.class, args);
    }

}