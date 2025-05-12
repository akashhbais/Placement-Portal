package com.svvv.placement.portal.controller;

import com.svvv.placement.portal.dto.StudentLoginRequest;
import com.svvv.placement.portal.dto.StudentRegistrationRequest;
import com.svvv.placement.portal.model.Student;
import com.svvv.placement.portal.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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

}
