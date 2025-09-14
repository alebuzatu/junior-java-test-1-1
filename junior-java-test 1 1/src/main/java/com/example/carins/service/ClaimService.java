package com.example.carins.service;

import com.example.carins.model.Car;
import com.example.carins.model.Claim;
import com.example.carins.repo.CarRepository;
import com.example.carins.repo.ClaimRepository;
import com.example.carins.web.dto.ClaimRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClaimService {
	private final ClaimRepository claimRepository;
    private final CarRepository carRepository;
    
    public ClaimService(ClaimRepository claimRepository, CarRepository carRepository) {
        this.claimRepository = claimRepository;
        this.carRepository = carRepository;
    }
    
    @Transactional
    public Claim createClaim(Long carId, ClaimRequestDto claimRequest) {
        // Găsește mașina sau aruncă excepție dacă nu există
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + carId));
        
        // Creează noul claim
        Claim claim = new Claim();
        claim.setCar(car);
        claim.setClaimDate(claimRequest.getClaimDate());
        claim.setDescription(claimRequest.getDescription());
        claim.setAmount(claimRequest.getAmount());
        
        // Salvează claim-ul în baza de date
        return claimRepository.save(claim);
    }
}
