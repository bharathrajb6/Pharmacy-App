package com.example.PharmacyApp.service;

import com.example.PharmacyApp.model.Wrappers.UserWrapper;
import com.example.PharmacyApp.model.Responses.ResponseMessage;
import com.example.PharmacyApp.model.Persistance.User;
import com.example.PharmacyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.PharmacyApp.utils.CommonUtils.createResponseMessage;
import static com.example.PharmacyApp.messages.UserInformation.*;
import static com.example.PharmacyApp.messages.CommonInformation.*;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserDetails(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public UserWrapper getUserInformation(String name) {
        User user = getUserDetails(name);
        if (user != null) {
            return generateUserWrapper(user);
        }
        return null;
    }

    public String updatePassword(String username, String newPassword) {
        User user = getUserDetails(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return PASSWORD_UPDATED;
    }

    public ResponseMessage updatePersonalInfo(String username, User request) {
        if (Objects.equals(username, request.getUsername())) {
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setContact(request.getContact());
            userRepository.save(user);
            return createResponseMessage(PERSONAL_INDO_UPDATED, 200, null, INFO);
        } else {
            return createResponseMessage(USERNAME_NOT_FOUND, 400, null, ERROR);
        }
    }

    public UserWrapper generateUserWrapper(User user) {
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setUserID(user.getUserID());
        userWrapper.setFirstName(user.getFirstName());
        userWrapper.setLastName(user.getLastName());
        userWrapper.setEmail(user.getEmail());
        userWrapper.setContact(user.getContact());
        userWrapper.setRole(user.getRole());
        return userWrapper;
    }
}
