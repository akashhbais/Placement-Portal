/**
 * 
 */
package com.svvv.placement.portal.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.svvv.placement.portal.model.Students;

/**
 * @author Akash Bais
 *
 */
@Repository
public interface StudentsRepository extends JpaRepository<Students, UUID> {
    
    // Find by enrollment number
    Students findByEnrollmentNumber(String enrollmentNumber);
    
    // Find by department
    List<Students> findByDepartment(String department);
    
    // Find by batch
    List<Students> findByBatch(String batch);
    
    // Find eligible Students with CGPA greater than specified value
    List<Students> findByEligibilityStatusAndCgpaGreaterThanEqual(
            Students.EligibilityStatus eligibilityStatus, BigDecimal minCgpa);
    
    // Find Studentss by placement status
    List<Students> findByPlacementStatus(Students.PlacementStatus placementStatus);
    
    // Custom query to find Students eligible for placement
    @Query("SELECT s FROM Students s WHERE s.eligibilityStatus = 'eligible' AND s.backlogs <= :maxBacklogs AND s.cgpa >= :minCgpa")
    List<Students> findEligibleStudentsForPlacement(Integer maxBacklogs, BigDecimal minCgpa);
}

