/**
 * 
 */
package com.svvv.placement.portal.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import com.svvv.placement.portal.model.Students.EligibilityStatus;
import com.svvv.placement.portal.model.Students.PlacementStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Akash Bais
 *
 */
@Getter
@Setter
public class StudentsDTO {
    private UUID id;
    private String enrollmentNumber;
    private String department;
    private String batch;
    private BigDecimal cgpa;
    private Integer backlogs;
    private String contact;
    private String address;
    private EligibilityStatus eligibilityStatus;
    private PlacementStatus placementStatus;
    private Map<String, Boolean> profileCompletionStatus;
    
}

