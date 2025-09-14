package com.example.carins.service;

import com.example.carins.model.InsurancePolicy;
import com.example.carins.repo.InsurancePolicyRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PolicyExpirationService {
    private final InsurancePolicyRepository insurancePolicyRepository;

    public PolicyExpirationService(InsurancePolicyRepository insurancePolicyRepository) {
        this.insurancePolicyRepository = insurancePolicyRepository;
    }

    /**
     * Verifică și marchează polițele expirate
     * Rulează zilnic la miezul nopții
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void checkAndMarkExpiredPolicies() {
        LocalDate today = LocalDate.now();
        List<InsurancePolicy> activePolicies = insurancePolicyRepository.findByIsActiveTrue();
        
        for (InsurancePolicy policy : activePolicies) {
            if (policy.getEndDate().isBefore(today)) {
                policy.setIsActive(false); // CORECT: setIsActive() nu setActive()
                insurancePolicyRepository.save(policy);
            }
        }
    }

    /**
     * Găsește toate polițele care expiră în următoarele X zile
     * @param daysBefore numărul de zile până la expirare
     * @return lista de polițe care expiră în curând
     */
    @Transactional(readOnly = true)
    public List<InsurancePolicy> findPoliciesExpiringSoon(int daysBefore) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(daysBefore);
        return insurancePolicyRepository.findByEndDateBetweenAndIsActiveTrue(startDate, endDate);
    }

    /**
     * Verifică dacă o poliță a expirat
     * @param policyId ID-ul poliței (de tip Long)
     * @return true dacă polița a expirat, false altfel
     */
    @Transactional(readOnly = true)
    public boolean isPolicyExpired(Long policyId) {
        InsurancePolicy policy = insurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found with id: " + policyId));
        
        return policy.getEndDate().isBefore(LocalDate.now());
    }

    /**
     * Reînnoiește o poliță expirată
     * @param policyId ID-ul poliței (de tip Long)
     * @param newEndDate noua dată de expirare
     * @return polița reînnoită
     */
    @Transactional
    public InsurancePolicy renewPolicy(Long policyId, LocalDate newEndDate) {
        InsurancePolicy policy = insurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found with id: " + policyId));
        
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(newEndDate);
        policy.setIsActive(true); // CORECT: setIsActive() nu setActive()
        
        return insurancePolicyRepository.save(policy);
    }
}