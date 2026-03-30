package com.example.quantitymeasurementapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QuantitymeasurementappApplication {

    private static final Logger logger = LoggerFactory.getLogger(QuantitymeasurementappApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QuantitymeasurementappApplication.class, args);
    }

}
