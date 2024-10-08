package com.example.PharmacyApp.service;

import com.example.PharmacyApp.model.Wrappers.UserWrapper;
import com.example.PharmacyApp.model.Persistance.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UserService userService;

    public UserWrapper getAdminDetails(String name) {
        User user = userService.getUserDetails(name);
        if (user != null) {
            return userService.generateUserWrapper(user);
        }
        return null;
    }
}
