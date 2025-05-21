/**
 * 
 */
package com.svvv.placement.portal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akash Bais
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAcademicDTO {
    private UUID id;
    private String degree;
    private String institution;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal cgpa;
    private BigDecimal percentage;
    private Boolean isCurrent;
}
