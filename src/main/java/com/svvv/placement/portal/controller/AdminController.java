package com.svvv.placement.portal.controller;

import com.svvv.placement.portal.dto.AdminLoginRequest;
import com.svvv.placement.portal.dto.AdminRegistrationRequest;
import com.svvv.placement.portal.model.Admin;
import com.svvv.placement.portal.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public String registerAdmin(@RequestBody AdminRegistrationRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }

        if (!request.getRegistrationKey().equals("YourSecretKey")) {
            return "Invalid admin registration key";
        }

        Admin admin = new Admin();
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setAdminId(request.getAdminId());
        admin.setPassword(request.getPassword()); // Ideally hash it

        adminService.registerAdmin(admin);

        return "Admin registered successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody AdminLoginRequest loginRequest) {
        Admin admin = adminService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (admin != null) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
