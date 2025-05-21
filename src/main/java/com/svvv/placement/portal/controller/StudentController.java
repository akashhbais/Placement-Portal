package com.svvv.placement.portal.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.svvv.placement.portal.dto.StudentLoginRequest;
import com.svvv.placement.portal.dto.StudentRegistrationRequest;
import com.svvv.placement.portal.dto.StudentsDTO;
import com.svvv.placement.portal.model.Student;
import com.svvv.placement.portal.model.Students.EligibilityStatus;
import com.svvv.placement.portal.model.Students.PlacementStatus;
import com.svvv.placement.portal.service.StudentService;
import com.svvv.placement.portal.service.StudentsService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentsService studentsService;

    public StudentController(StudentService studentService,StudentsService studentsService) {
        this.studentService = studentService;
        this.studentsService = studentsService;
    }

    @PostMapping("/register")
    public String registerStudent(@RequestBody StudentRegistrationRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }

        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setEnrollmentNumber(request.getEnrollmentNumber());
        student.setPassword(request.getPassword()); // Ideally hash this

        studentService.registerStudent(student);
        return "Student registered successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginStudent(@RequestBody StudentLoginRequest loginRequest) {
        Student student = studentService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (student != null) {
            return ResponseEntity.ok("Student login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    
   
    
    @PostMapping
    public ResponseEntity<StudentsDTO> createStudent(@RequestBody StudentsDTO StudentsDTO) {
        StudentsDTO createdStudent = studentsService.createStudent(StudentsDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    
    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentsDTO> getStudentById(@PathVariable UUID id) {
        StudentsDTO student = studentsService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    // Get student by enrollment number
    @GetMapping("/enrollment/{enrollmentNumber}")
    public ResponseEntity<StudentsDTO> getStudentByEnrollmentNumber(@PathVariable String enrollmentNumber) {
        StudentsDTO student = studentsService.getStudentByEnrollmentNumber(enrollmentNumber);
        return ResponseEntity.ok(student);
    }
    
    // Get all students
    @GetMapping
    public ResponseEntity<List<StudentsDTO>> getAllStudents() {
        List<StudentsDTO> students = studentsService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<StudentsDTO> updateStudent(@PathVariable UUID id, @RequestBody StudentsDTO StudentsDTO) {
        StudentsDTO updatedStudent = studentsService.updateStudent(id, StudentsDTO);
        return ResponseEntity.ok(updatedStudent);
    }
    
    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentsService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    
    // Get students by department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentsDTO>> getStudentsByDepartment(@PathVariable String department) {
        List<StudentsDTO> students = studentsService.getStudentsByDepartment(department);
        return ResponseEntity.ok(students);
    }
    
    // Get students by batch
    @GetMapping("/batch/{batch}")
    public ResponseEntity<List<StudentsDTO>> getStudentsByBatch(@PathVariable String batch) {
        List<StudentsDTO> students = studentsService.getStudentsByBatch(batch);
        return ResponseEntity.ok(students);
    }
    
    // Get eligible students with minimum CGPA
    @GetMapping("/eligible")
    public ResponseEntity<List<StudentsDTO>> getEligibleStudentsWithMinCgpa(
            @RequestParam(defaultValue = "eligible") EligibilityStatus status,
            @RequestParam BigDecimal minCgpa) {
        List<StudentsDTO> students = studentsService.getEligibleStudentsWithMinCgpa(status, minCgpa);
        return ResponseEntity.ok(students);
    }
    
    // Get students by placement status
    @GetMapping("/placement-status/{status}")
    public ResponseEntity<List<StudentsDTO>> getStudentsByPlacementStatus(@PathVariable PlacementStatus status) {
        List<StudentsDTO> students = studentsService.getStudentsByPlacementStatus(status);
        return ResponseEntity.ok(students);
    }
    
    // Get eligible students for placement
    @GetMapping("/eligible-for-placement")
    public ResponseEntity<List<StudentsDTO>> getEligibleStudentsForPlacement(
            @RequestParam(defaultValue = "0") Integer maxBacklogs,
            @RequestParam BigDecimal minCgpa) {
        List<StudentsDTO> students = studentsService.getEligibleStudentsForPlacement(maxBacklogs, minCgpa);
        return ResponseEntity.ok(students);
    }
    
    // Update profile completion section
    @PatchMapping("/{id}/profile-completion")
    public ResponseEntity<StudentsDTO> updateProfileCompletionSection(
            @PathVariable UUID id,
            @RequestBody Map<String, Boolean> sectionUpdate) {
        // Expecting a JSON like {"section": "personal", "completed": true}
        String section = sectionUpdate.containsKey("section") ? sectionUpdate.get("section").toString() : null;
        Boolean completed = sectionUpdate.get("completed");
        
        if (section == null || completed == null) {
            return ResponseEntity.badRequest().build();
        }
        
        StudentsDTO updatedStudent = studentsService.updateProfileCompletionSection(id, section, completed);
        return ResponseEntity.ok(updatedStudent);
    }

}
