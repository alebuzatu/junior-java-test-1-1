package com.example.carins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.carins.service.DataFixService;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CarInsuranceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarInsuranceApplication.class, args);
    }
    @Bean
    public CommandLineRunner run(DataFixService dataFixService) {
        return args -> {
            dataFixService.fixOpenEndedPolicies();
        };
    }

}