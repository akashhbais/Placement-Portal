/**
 * 
 */
package com.svvv.placement.portal.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akash Bais
 *
 */
@Entity
@Table(name = "student_academics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAcademic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String degree;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(precision = 3, scale = 2)
    private BigDecimal cgpa;

    @Column(precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(name = "is_current")
    private Boolean isCurrent = false;
}
