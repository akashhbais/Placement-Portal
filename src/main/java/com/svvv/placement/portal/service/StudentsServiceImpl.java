/**
 * 
 */
package com.svvv.placement.portal.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svvv.placement.portal.dto.StudentsDTO;
import com.svvv.placement.portal.dto.StudentsMapper;
import com.svvv.placement.portal.exception.ResourceNotFoundException;
import com.svvv.placement.portal.model.Students;
import com.svvv.placement.portal.model.Students.EligibilityStatus;
import com.svvv.placement.portal.model.Students.PlacementStatus;
import com.svvv.placement.portal.repository.StudentsRepository;

import jakarta.transaction.Transactional;

/**
 * @author Akash Bais
 *
 */
@Service
public class StudentsServiceImpl implements StudentsService {

    private final StudentsRepository studentsRepository;
    private final StudentsMapper studentsMapper;

    @Autowired
    public StudentsServiceImpl(StudentsRepository studentsRepository, StudentsMapper studentsMapper) {
        this.studentsRepository = studentsRepository;
        this.studentsMapper = studentsMapper;
    }

    @Override
    @Transactional
    public StudentsDTO createStudent(StudentsDTO studentsDTO) {
        // Generate UUID if not provided
        if (studentsDTO.getId() == null) {
            studentsDTO.setId(UUID.randomUUID());
        }

        // Initialize profile completion status if not provided
        if (studentsDTO.getProfileCompletionStatus() == null) {
            Map<String, Boolean> profileStatus = new HashMap<>();
            profileStatus.put("personal", false);
            profileStatus.put("academic", false);
            profileStatus.put("skills", false);
            profileStatus.put("links", false);
            studentsDTO.setProfileCompletionStatus(profileStatus);
        }

        Students student = studentsMapper.toEntity(studentsDTO);
        Students savedStudent = studentsRepository.save(student);
        return studentsMapper.toDTO(savedStudent);
    }

    @Override
    public StudentsDTO getStudentById(UUID id) {
        Students student = studentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return studentsMapper.toDTO(student);
    }

    @Override
    public StudentsDTO getStudentByEnrollmentNumber(String enrollmentNumber) {
        Students student = studentsRepository.findByEnrollmentNumber(enrollmentNumber);
        if (student == null) {
            throw new ResourceNotFoundException("Student not found with enrollment number: " + enrollmentNumber);
        }
        return studentsMapper.toDTO(student);
    }

    @Override
    public List<StudentsDTO> getAllStudents() {
        List<Students> studentsList = studentsRepository.findAll();
        return studentsList.stream().map(studentsMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentsDTO updateStudent(UUID id, StudentsDTO studentsDTO) {
        Students student = studentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        studentsMapper.updateEntityFromDTO(studentsDTO, student);
        Students updatedStudent = studentsRepository.save(student);

        return studentsMapper.toDTO(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(UUID id) {
        if (!studentsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentsRepository.deleteById(id);
    }

    @Override
    public List<StudentsDTO> getStudentsByDepartment(String department) {
        List<Students> studentsList = studentsRepository.findByDepartment(department);
        return studentsList.stream().map(studentsMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<StudentsDTO> getStudentsByBatch(String batch) {
        List<Students> studentsList = studentsRepository.findByBatch(batch);
        return studentsList.stream().map(studentsMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<StudentsDTO> getEligibleStudentsWithMinCgpa(EligibilityStatus status, BigDecimal minCgpa) {
        List<Students> studentsList = studentsRepository.findByEligibilityStatusAndCgpaGreaterThanEqual(status, minCgpa);
        return studentsList.stream().map(studentsMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<StudentsDTO> getStudentsByPlacementStatus(PlacementStatus placementStatus) {
        List<Students> studentsList = studentsRepository.findByPlacementStatus(placementStatus);
        return studentsList.stream().map(studentsMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<StudentsDTO> getEligibleStudentsForPlacement(Integer maxBacklogs, BigDecimal minCgpa) {
        List<Students> studentsList = studentsRepository.findEligibleStudentsForPlacement(maxBacklogs, minCgpa);
        return studentsList.stream().map(studentsMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentsDTO updateProfileCompletionSection(UUID id, String section, boolean completed) {
        Students student = studentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        Map<String, Boolean> profileStatus = student.getProfileCompletionStatus();
        if (profileStatus == null) {
            profileStatus = new HashMap<>();
        }

        profileStatus.put(section, completed);
        student.setProfileCompletionStatus(profileStatus);

        Students updatedStudent = studentsRepository.save(student);
        return studentsMapper.toDTO(updatedStudent);
    }
}