package com.example.carins.web.dto;

import com.example.carins.model.Claim;
import com.example.carins.model.InsurancePolicy;
import com.example.carins.model.Owner;
import java.util.List;

public class CarHistoryDto {
	private Long carId;
    private String vin;
    private String make;
    private String model;
    private Integer yearOfManufacture;
    private Owner currentOwner;
    private List<InsurancePolicy> insurancePolicies;
    private List<Claim> claims;
    
    // Constructor gol
    public CarHistoryDto() {}
    
    // Getters and setters
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
    
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public Integer getYearOfManufacture() { return yearOfManufacture; }
    public void setYearOfManufacture(Integer yearOfManufacture) { this.yearOfManufacture = yearOfManufacture; }
    
    public Owner getCurrentOwner() { return currentOwner; }
    public void setCurrentOwner(Owner currentOwner) { this.currentOwner = currentOwner; }
    
    public List<InsurancePolicy> getInsurancePolicies() { return insurancePolicies; }
    public void setInsurancePolicies(List<InsurancePolicy> insurancePolicies) { this.insurancePolicies = insurancePolicies; }
    
    public List<Claim> getClaims() { return claims; }
    public void setClaims(List<Claim> claims) { this.claims = claims; }
}
