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

	private final StudentsRepository StudentsRepository;
	private final StudentsMapper StudentsMapper;

	@Autowired
	public StudentsServiceImpl(StudentsRepository StudentsRepository, StudentsMapper StudentsMapper) {
		this.StudentsRepository = StudentsRepository;
		this.StudentsMapper = StudentsMapper;
	}

	@Override
	@Transactional
	public StudentsDTO createStudent(StudentsDTO StudentsDTO) {
		// Generate UUID if not provided
		if (StudentsDTO.getId() == null) {
			StudentsDTO.setId(UUID.randomUUID());
		}

		// Initialize profile completion status if not provided
		if (StudentsDTO.getProfileCompletionStatus() == null) {
			Map<String, Boolean> profileStatus = new HashMap<>();
			profileStatus.put("personal", false);
			profileStatus.put("academic", false);
			profileStatus.put("skills", false);
			profileStatus.put("links", false);
			StudentsDTO.setProfileCompletionStatus(profileStatus);
		}

		Students Students = StudentsMapper.toEntity(StudentsDTO);
		Students savedStudents = StudentsRepository.save(Students);
		return StudentsMapper.toDTO(savedStudents);
	}

	@Override
	public StudentsDTO getStudentById(UUID id) {
		Students Students = StudentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Students not found with id: " + id));
		return StudentsMapper.toDTO(Students);
	}

	@Override
	public StudentsDTO getStudentByEnrollmentNumber(String enrollmentNumber) {
		Students Students = StudentsRepository.findByEnrollmentNumber(enrollmentNumber);
		if (Students == null) {
			throw new ResourceNotFoundException("Students not found with enrollment number: " + enrollmentNumber);
		}
		return StudentsMapper.toDTO(Students);
	}

	@Override
	public List<StudentsDTO> getAllStudents() {
		List<Students> StudentsList = StudentsRepository.findAll();
		return StudentsList.stream().map(StudentsMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public StudentsDTO updateStudent(UUID id, StudentsDTO StudentsDTO) {
		Students Students = StudentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Students not found with id: " + id));

		StudentsMapper.updateEntityFromDTO(StudentsDTO, Students);
		Students updatedStudents = StudentsRepository.save(Students);

		return StudentsMapper.toDTO(updatedStudents);
	}

	@Override
	@Transactional
	public void deleteStudent(UUID id) {
		if (!StudentsRepository.existsById(id)) {
			throw new ResourceNotFoundException("Students not found with id: " + id);
		}
		StudentsRepository.deleteById(id);
	}

	@Override
	public List<StudentsDTO> getStudentsByDepartment(String department) {
		List<Students> StudentsList = StudentsRepository.findByDepartment(department);
		return StudentsList.stream().map(StudentsMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<StudentsDTO> getStudentsByBatch(String batch) {
		List<Students> StudentsList = StudentsRepository.findByBatch(batch);
		return StudentsList.stream().map(StudentsMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<StudentsDTO> getEligibleStudentsWithMinCgpa(EligibilityStatus status, BigDecimal minCgpa) {
		List<Students> StudentsList = StudentsRepository.findByEligibilityStatusAndCgpaGreaterThanEqual(status, minCgpa);
		return StudentsList.stream().map(StudentsMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<StudentsDTO> getStudentsByPlacementStatus(PlacementStatus placementStatus) {
		List<Students> StudentsList = StudentsRepository.findByPlacementStatus(placementStatus);
		return StudentsList.stream().map(StudentsMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<StudentsDTO> getEligibleStudentsForPlacement(Integer maxBacklogs, BigDecimal minCgpa) {
		List<Students> StudentsList = StudentsRepository.findEligibleStudentsForPlacement(maxBacklogs, minCgpa);
		return StudentsList.stream().map(StudentsMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public StudentsDTO updateProfileCompletionSection(UUID id, String section, boolean completed) {
		Students Students = StudentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Students not found with id: " + id));

		Map<String, Boolean> profileStatus = Students.getProfileCompletionStatus();
		if (profileStatus == null) {
			profileStatus = new HashMap<>();
		}

		profileStatus.put(section, completed);
		Students.setProfileCompletionStatus(profileStatus);

		Students updatedStudents = StudentsRepository.save(Students);
		return StudentsMapper.toDTO(updatedStudents);
	}
}