/**
 * 
 */
package com.svvv.placement.portal.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Akash Bais
 *
 */
@Entity
@Table(name = "students")
@Getter
@Setter
public class Students {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Id
    private UUID id;
    
    @Column(name = "enrollment_number", unique = true, nullable = false, length = 20)
    private String enrollmentNumber;
    
    @Column(name = "department", nullable = false, length = 100)
    private String department;
    
    @Column(name = "batch", nullable = false, length = 20)
    private String batch;
    
    @Column(name = "cgpa", nullable = false, precision = 3, scale = 2)
    private BigDecimal cgpa;
    
    @Column(name = "backlogs")
    private Integer backlogs = 0;
    
    @Column(name = "contact", length = 20)
    private String contact;
    
    @Column(name = "address")
    private String address;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "eligibility_status")
    private EligibilityStatus eligibilityStatus = EligibilityStatus.eligible;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "placement_status")
    private PlacementStatus placementStatus = PlacementStatus.not_placed;
    
    @Column(name = "profile_completion_status", columnDefinition = "jsonb")
    private String profileCompletionStatusJson;
    
    public enum EligibilityStatus {
        eligible, ineligible
    }
    
    public enum PlacementStatus {
        not_placed, placed, internship
    }
    
    // Transient methods for working with the Map object (used in your code)
    @Transient
    public Map<String, Boolean> getProfileCompletionStatus() {
        if (profileCompletionStatusJson == null || profileCompletionStatusJson.isEmpty()) {
            return null;
        }
        
        try {
            return objectMapper.readValue(
                profileCompletionStatusJson, 
                new TypeReference<Map<String, Boolean>>() {}
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing profile completion status", e);
        }
    }
    
    @Transient
    public void setProfileCompletionStatus(Map<String, Boolean> profileCompletionStatus) {
        try {
            this.profileCompletionStatusJson = 
                profileCompletionStatus != null ? 
                objectMapper.writeValueAsString(profileCompletionStatus) : 
                null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing profile completion status", e);
        }
    }
    
    // You might need these converter methods for JPA Callbacks
    @jakarta.persistence.PrePersist
    @jakarta.persistence.PreUpdate
    public void convertMapToJson() {
        // This will ensure the JSON field is updated from the transient map if it exists
        if (getProfileCompletionStatus() != null) {
            try {
                this.profileCompletionStatusJson = objectMapper.writeValueAsString(getProfileCompletionStatus());
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializing profile completion status", e);
            }
        }
    }
}