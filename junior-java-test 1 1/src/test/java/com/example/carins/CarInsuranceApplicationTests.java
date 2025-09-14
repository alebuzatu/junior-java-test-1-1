package com.example.carins;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CarInsuranceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testInsuranceValid_CarNotFound() {
        // Testează răspunsul pentru o mașină care nu există
        Long nonExistentCarId = 999L;
        LocalDate testDate = LocalDate.of(2025, 6, 1);
        
        ResponseEntity<?> response = restTemplate.getForEntity(
            "/api/cars/" + nonExistentCarId + "/insurance-valid?date=" + testDate,
            Object.class
        );
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testInsuranceValid_DateTooEarly() {
        // Testează răspunsul pentru o dată prea veche
        Long carId = 1L;
        LocalDate tooEarlyDate = LocalDate.of(1899, 12, 31);
        
        ResponseEntity<?> response = restTemplate.getForEntity(
            "/api/cars/" + carId + "/insurance-valid?date=" + tooEarlyDate,
            Object.class
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testInsuranceValid_DateTooLate() {
        // Testează răspunsul pentru o dată prea îndepărtată în viitor
        Long carId = 1L;
        LocalDate tooLateDate = LocalDate.of(2101, 1, 1);
        
        ResponseEntity<?> response = restTemplate.getForEntity(
            "/api/cars/" + carId + "/insurance-valid?date=" + tooLateDate,
            Object.class
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}