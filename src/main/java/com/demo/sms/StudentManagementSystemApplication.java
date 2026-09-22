package com.demo.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.rmvn spring-boot:runun(StudentManagementSystemApplication.class, args);
    }
}
