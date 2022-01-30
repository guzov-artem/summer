package com.vtr.camera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class CameraServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CameraServiceApplication.class, args);
    }

}
