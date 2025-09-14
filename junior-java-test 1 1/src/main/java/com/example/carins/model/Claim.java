package com.example.carins.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Claim {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @ManyToOne
	    @JoinColumn(name = "car_id", nullable = false)
	    private Car car;
	    
	    @NotNull(message = "Claim date is required")
	    private LocalDate claimDate;
	    
	    @NotBlank(message = "Description is required")
	    private String description;
	    
	    @NotNull(message = "Amount is required")
	    @Positive(message = "Amount must be positive")
	    private BigDecimal amount;
	    
	    private LocalDate createdAt = LocalDate.now();
	    
	    // Constructor gol (necesar pentru JPA)
	    public Claim() {}
	    
	    // Constructor cu parametri
	    public Claim(Car car, LocalDate claimDate, String description, BigDecimal amount) {
	        this.car = car;
	        this.claimDate = claimDate;
	        this.description = description;
	        this.amount = amount;
	    }
	    
	    // Getters and setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    
	    public Car getCar() { return car; }
	    public void setCar(Car car) { this.car = car; }
	    
	    public LocalDate getClaimDate() { return claimDate; }
	    public void setClaimDate(LocalDate claimDate) { this.claimDate = claimDate; }
	    
	    public String getDescription() { return description; }
	    public void setDescription(String description) { this.description = description; }
	    
	    public BigDecimal getAmount() { return amount; }
	    public void setAmount(BigDecimal amount) { this.amount = amount; }
	    
	    public LocalDate getCreatedAt() { return createdAt; }
	    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

}
