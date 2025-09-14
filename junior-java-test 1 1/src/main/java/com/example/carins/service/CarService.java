package com.example.carins.service;

import com.example.carins.model.Car;
import com.example.carins.model.Claim;
import com.example.carins.model.InsurancePolicy;
import com.example.carins.repo.CarRepository;
import com.example.carins.repo.ClaimRepository;
import com.example.carins.repo.InsurancePolicyRepository;
import com.example.carins.web.dto.CarHistoryDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final InsurancePolicyRepository policyRepository;
    private final ClaimRepository claimRepository; //adaugat
    
    public CarService(CarRepository carRepository, InsurancePolicyRepository policyRepository, ClaimRepository claimRepository) {
        this.carRepository = carRepository;
        this.policyRepository = policyRepository;
        this.claimRepository = claimRepository; //adaugat
    }

    public List<Car> listCars() {
        return carRepository.findAll();
    }

    public boolean isInsuranceValid(Long carId, LocalDate date) {
        if (carId == null || date == null) return false;
        // TODO: optionally throw NotFound if car does not exist
        return policyRepository.existsActiveOnDate(carId, date);
    }
    
    // METODA NOUĂ PENTRU ISTORIC
    public CarHistoryDto getCarHistory(Long carId) {
        // Găsește mașina sau aruncă excepție dacă nu există
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + carId));
        
        // Găsește toate politicile de asigurare pentru mașină
        List<InsurancePolicy> policies = policyRepository.findByCarId(carId);
        
        // Găsește toate claim-urile pentru mașină, ordonate după dată
        List<Claim> claims = claimRepository.findByCarIdOrderByClaimDateDesc(carId);
        
        // Creează și populează DTO-ul pentru răspuns
        CarHistoryDto history = new CarHistoryDto();
        history.setCarId(car.getId());
        history.setVin(car.getVin());
        history.setMake(car.getMake());
        history.setModel(car.getModel());
        history.setYearOfManufacture(car.getYearOfManufacture());
        history.setCurrentOwner(car.getOwner());
        history.setInsurancePolicies(policies);
        history.setClaims(claims);
        
        return history;
    }
}
