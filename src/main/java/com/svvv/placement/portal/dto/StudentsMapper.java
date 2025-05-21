/**
 * 
 */
package com.svvv.placement.portal.dto;

import com.svvv.placement.portal.model.Students;

/**
 * @author Akash Bais
 *
 */
public class StudentsMapper {
    
    public StudentsDTO toDTO(Students student) {
        if (student == null) {
            return null;
        }
        
        StudentsDTO dto = new StudentsDTO();
        dto.setId(student.getId());
        dto.setEnrollmentNumber(student.getEnrollmentNumber());
        dto.setDepartment(student.getDepartment());
        dto.setBatch(student.getBatch());
        dto.setCgpa(student.getCgpa());
        dto.setBacklogs(student.getBacklogs());
        dto.setContact(student.getContact());
        dto.setAddress(student.getAddress());
        dto.setEligibilityStatus(student.getEligibilityStatus());
        dto.setPlacementStatus(student.getPlacementStatus());
        dto.setProfileCompletionStatus(student.getProfileCompletionStatus());
        
        return dto;
    }
    
    public Students toEntity(StudentsDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Students student = new Students();
        student.setId(dto.getId());
        student.setEnrollmentNumber(dto.getEnrollmentNumber());
        student.setDepartment(dto.getDepartment());
        student.setBatch(dto.getBatch());
        student.setCgpa(dto.getCgpa());
        student.setBacklogs(dto.getBacklogs());
        student.setContact(dto.getContact());
        student.setAddress(dto.getAddress());
        student.setEligibilityStatus(dto.getEligibilityStatus());
        student.setPlacementStatus(dto.getPlacementStatus());
        student.setProfileCompletionStatus(dto.getProfileCompletionStatus());
        
        return student;
    }
    
    public void updateEntityFromDTO(StudentsDTO dto, Students student) {
        if (dto.getEnrollmentNumber() != null) {
            student.setEnrollmentNumber(dto.getEnrollmentNumber());
        }
        if (dto.getDepartment() != null) {
            student.setDepartment(dto.getDepartment());
        }
        if (dto.getBatch() != null) {
            student.setBatch(dto.getBatch());
        }
        if (dto.getCgpa() != null) {
            student.setCgpa(dto.getCgpa());
        }
        if (dto.getBacklogs() != null) {
            student.setBacklogs(dto.getBacklogs());
        }
        if (dto.getContact() != null) {
            student.setContact(dto.getContact());
        }
        if (dto.getAddress() != null) {
            student.setAddress(dto.getAddress());
        }
        if (dto.getEligibilityStatus() != null) {
            student.setEligibilityStatus(dto.getEligibilityStatus());
        }
        if (dto.getPlacementStatus() != null) {
            student.setPlacementStatus(dto.getPlacementStatus());
        }
        if (dto.getProfileCompletionStatus() != null) {
            student.setProfileCompletionStatus(dto.getProfileCompletionStatus());
        }
    }
}
