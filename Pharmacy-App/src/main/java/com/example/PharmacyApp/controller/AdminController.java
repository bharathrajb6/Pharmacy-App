package com.example.PharmacyApp.controller;

import com.example.PharmacyApp.model.Wrappers.UserWrapper;
import com.example.PharmacyApp.model.Persistance.User;
import com.example.PharmacyApp.model.Responses.ResponseMessage;
import com.example.PharmacyApp.service.AdminService;
import com.example.PharmacyApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/v1/admin/")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/getAdminDetails", method = RequestMethod.GET)
    public ResponseEntity<UserWrapper> getAdminDetails(Authentication authentication) {
        return ResponseEntity.ok(adminService.getAdminDetails(authentication.getName()));
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResponseEntity<String> updatePassword(Authentication authentication, @RequestBody String newPassword) {
        return ResponseEntity.ok(userService.updatePassword(authentication.getName(), newPassword));
    }

    @RequestMapping(value = "/updateInformation", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> updateInfo(Authentication authentication, @RequestBody User user) {
        return ResponseEntity.ok(userService.updatePersonalInfo(authentication.getName(), user));
    }
}
