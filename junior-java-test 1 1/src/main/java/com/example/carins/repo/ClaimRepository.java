package com.example.carins.repo;

import com.example.carins.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    
    // Găsește toate claim-urile pentru o mașină, ordonate după dată (descrescător)
    List<Claim> findByCarIdOrderByClaimDateDesc(Long carId);
    
    // Numără câte claim-uri are o mașină
    int countByCarId(Long carId);
}