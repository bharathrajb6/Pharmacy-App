package com.example.PharmacyApp.controller;

import com.example.PharmacyApp.model.Wrappers.UserWrapper;
import com.example.PharmacyApp.model.Persistance.Medication;
import com.example.PharmacyApp.model.Responses.ResponseMessage;
import com.example.PharmacyApp.model.Persistance.User;
import com.example.PharmacyApp.service.MedicationService;
import com.example.PharmacyApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MedicationService medicationService;

    @GetMapping("home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome user..");
    }

    @GetMapping("getUserDetails")
    public ResponseEntity<UserWrapper> getUserDetails(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserInformation(authentication.getName()));
    }

    @PostMapping("updatePassword")
    public ResponseEntity<String> updatePassword(Authentication authentication, @RequestBody String newPassword) {
        return ResponseEntity.ok(userService.updatePassword(authentication.getName(), newPassword));
    }

    @GetMapping("medication/getByName/{name}")
    public ResponseEntity<Medication> getMedicationDetailsByName(@PathVariable String name) {
        return ResponseEntity.ok(medicationService.getMedicationDetailsByName(name));
    }

    @GetMapping("medication/getById/{id}")
    public ResponseEntity<Medication> getMedicationDetailsById(@PathVariable Integer id) {
        return ResponseEntity.ok(medicationService.getMedicationDetailsById(id));
    }

    @PostMapping("updateInfo")
    public ResponseEntity<ResponseMessage> updatePersonalInfo(Authentication authentication, @RequestBody User request) {
        return ResponseEntity.ok(userService.updatePersonalInfo(authentication.getName(), request));
    }
}
