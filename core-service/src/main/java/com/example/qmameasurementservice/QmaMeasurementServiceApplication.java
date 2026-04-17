package com.example.qmameasurementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QmaMeasurementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QmaMeasurementServiceApplication.class, args);
    }

}
