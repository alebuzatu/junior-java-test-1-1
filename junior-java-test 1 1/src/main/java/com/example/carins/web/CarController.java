package com.example.carins.web;

import com.example.carins.model.Car;
import com.example.carins.service.CarService;
import com.example.carins.web.dto.CarDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.carins.web.dto.CarHistoryDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import com.example.carins.repo.CarRepository; 
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService service;
    private final CarRepository carRepository; // ADĂUGAT: Repository pentru validare

    public CarController(CarService service, CarRepository carRepository) {
        this.service = service;
        this.carRepository = carRepository;
    }

    @GetMapping("/cars")
    public List<CarDto> getCars() {
        return service.listCars().stream().map(this::toDto).toList();
    }

    // MODIFICAT COMPLET: Endpoint-ul cu validări
    @GetMapping("/cars/{carId}/insurance-valid")
    public ResponseEntity<?> isInsuranceValid(
            @PathVariable Long carId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        // Validare carId există
        if (!carRepository.existsById(carId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Car with id " + carId + " not found"));
        }
        
        // Validare interval dată
        if (date.isBefore(LocalDate.of(1900, 1, 1))) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Date cannot be before 1900-01-01"));
        }

        if (date.isAfter(LocalDate.of(2100, 12, 31))) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Date cannot be after 2100-12-31"));
        }
        
        boolean valid = service.isInsuranceValid(carId, date);
        return ResponseEntity.ok(new InsuranceValidityResponse(carId, date.toString(), valid));
    }

    @GetMapping("/cars/{carId}/history")
    public ResponseEntity<CarHistoryDto> getCarHistory(@PathVariable Long carId) {
        CarHistoryDto history = service.getCarHistory(carId);
        return ResponseEntity.ok(history);
    }
    
    private CarDto toDto(Car c) {
        var o = c.getOwner();
        return new CarDto(c.getId(), c.getVin(), c.getMake(), c.getModel(), c.getYearOfManufacture(),
                o != null ? o.getId() : null,
                o != null ? o.getName() : null,
                o != null ? o.getEmail() : null);
    }

    public record InsuranceValidityResponse(Long carId, String date, boolean valid) {}
}
