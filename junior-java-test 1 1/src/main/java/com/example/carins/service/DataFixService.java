package com.example.carins.service;

import com.example.carins.model.InsurancePolicy;
import com.example.carins.repo.InsurancePolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class DataFixService {
private final InsurancePolicyRepository insurancePolicyRepository;
    
    public DataFixService(InsurancePolicyRepository insurancePolicyRepository) {
        this.insurancePolicyRepository = insurancePolicyRepository;
    }
    
    @Transactional
    public void fixOpenEndedPolicies() {
        List<InsurancePolicy> policies = insurancePolicyRepository.findPoliciesWithNullEndDate();
        
        for (InsurancePolicy policy : policies) {
            policy.setEndDate(policy.getStartDate().plusYears(1));
            insurancePolicyRepository.save(policy);
        }
        
        if (!policies.isEmpty()) {
            System.out.println("Fixed " + policies.size() + " policies with null endDate");
        }
    }

}
