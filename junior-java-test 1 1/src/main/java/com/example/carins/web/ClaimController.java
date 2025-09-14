package com.example.carins.web;

import com.example.carins.model.Claim;
import com.example.carins.service.ClaimService;
import com.example.carins.web.dto.ClaimRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api") // SCHIMBĂ AICI
public class ClaimController {
    
    private final ClaimService claimService;
    
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }
    
    @PostMapping("/cars/{carId}/claims") // ADAPTEAZĂ RUTA
    public ResponseEntity<Claim> createClaim(
            @PathVariable Long carId,
            @Valid @RequestBody ClaimRequestDto claimRequest) {
        
        Claim claim = claimService.createClaim(carId, claimRequest);
        
        return ResponseEntity
                .created(URI.create("/api/cars/" + carId + "/claims/" + claim.getId()))
                .body(claim);
    }
}