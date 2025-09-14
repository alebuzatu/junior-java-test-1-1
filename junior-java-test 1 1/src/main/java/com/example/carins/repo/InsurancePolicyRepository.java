package com.example.carins.repo;

import com.example.carins.model.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {

    @Query("select case when count(p) > 0 then true else false end " +
           "from InsurancePolicy p " +
           "where p.car.id = :carId " +
           "and :date BETWEEN p.startDate AND p.endDate") //modificare metodei pentru a nu mai permite enddate NULL
    boolean existsActiveOnDate(@Param("carId") Long carId, @Param("date") LocalDate date);

    List<InsurancePolicy> findByCarId(Long carId);
    
    //Metoda pentru a gasi endDate null si pentru a le repara 
    @Query("SELECT ip FROM InsurancePolicy ip WHERE ip.endDate IS NULL")
    List<InsurancePolicy> findPoliciesWithNullEndDate();
    
    //metoda pt a gasi polite valide pentru o masina la o anumita data 
    @Query("SELECT ip FROM InsurancePolicy ip WHERE ip.car.id = :carId AND :date BETWEEN ip.startDate AND ip.endDate")
    List<InsurancePolicy> findValidPoliciesForCarOnDate(@Param("carId") Long carId, @Param("date") LocalDate date);
    
    // METODE NOI PENTRU PolicyExpirationService
    List<InsurancePolicy> findByIsActiveTrue();
    
    @Query("SELECT ip FROM InsurancePolicy ip WHERE ip.endDate BETWEEN :startDate AND :endDate AND ip.isActive = true")
    List<InsurancePolicy> findByEndDateBetweenAndIsActiveTrue(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}