package com.example.PharmacyApp.controller;import com.example.PharmacyApp.model.Medication;import com.example.PharmacyApp.model.ResponseMessage;import com.example.PharmacyApp.model.User;import com.example.PharmacyApp.service.MedicationService;import com.example.PharmacyApp.service.UserService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.ResponseEntity;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;@RestController@RequestMapping("user")public class UserController {    @Autowired    private UserService userService;    @Autowired    private MedicationService medicationService;    @GetMapping("home")    public ResponseEntity<String> home() {        return ResponseEntity.ok("Welcome user..");    }    @GetMapping("getUserDetails")    public ResponseEntity<User> getUserDetails(Authentication authentication) {        return ResponseEntity.ok(userService.getUserDetails(authentication.getName()));    }    @PostMapping("updatePassword")    public ResponseEntity<String> updatePassword(Authentication authentication, @RequestBody String newPassword) {        return ResponseEntity.ok(userService.updatePassword(authentication.getName(), newPassword));    }    @GetMapping("medication/getByName/{name}")    public ResponseEntity<Medication> getMedicationDetailsByName(@PathVariable String name) {        return ResponseEntity.ok(medicationService.getMedicationDetailsByName(name));    }    @PostMapping("/updateInfo")    public ResponseEntity<ResponseMessage> updatePersonalInfo(Authentication authentication,@RequestBody User request) {        return ResponseEntity.ok(userService.updatePersonalInfo(authentication.getName(),request));    }}