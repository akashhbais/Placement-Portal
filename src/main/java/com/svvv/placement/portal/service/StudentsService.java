/**
 * 
 */
package com.svvv.placement.portal.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.svvv.placement.portal.dto.StudentsDTO;
import com.svvv.placement.portal.model.Students.EligibilityStatus;
import com.svvv.placement.portal.model.Students.PlacementStatus;

/**
 * @author Akash Bais
 *
 */
public interface StudentsService {
    
    // CRUD operations
    StudentsDTO createStudent(StudentsDTO StudentsDTO);
    
    StudentsDTO getStudentById(UUID id);
    
    StudentsDTO getStudentByEnrollmentNumber(String enrollmentNumber);
    
    List<StudentsDTO> getAllStudents();
    
    StudentsDTO updateStudent(UUID id, StudentsDTO StudentsDTO);
    
    void deleteStudent(UUID id);
    
    // Additional business operations
    List<StudentsDTO> getStudentsByDepartment(String department);
    
    List<StudentsDTO> getStudentsByBatch(String batch);
    
    List<StudentsDTO> getEligibleStudentsWithMinCgpa(EligibilityStatus status, BigDecimal minCgpa);
    
    List<StudentsDTO> getStudentsByPlacementStatus(PlacementStatus placementStatus);
    
    List<StudentsDTO> getEligibleStudentsForPlacement(Integer maxBacklogs, BigDecimal minCgpa);
    
    // Profile completion status operations
    StudentsDTO updateProfileCompletionSection(UUID id, String section, boolean completed);
}
