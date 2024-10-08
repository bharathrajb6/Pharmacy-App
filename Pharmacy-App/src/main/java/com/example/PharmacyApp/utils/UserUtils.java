package com.example.PharmacyApp.utils;

import com.example.PharmacyApp.model.Persistance.Role;
import com.example.PharmacyApp.model.Persistance.User;
import com.example.PharmacyApp.model.Responses.ResponseMessage;
import com.example.PharmacyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.PharmacyApp.messages.CommonInformation.*;
import static com.example.PharmacyApp.messages.UserInformation.*;

@Service
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

    public ResponseMessage validateUserDetails(User user) {
        if (user.getFirstName().isEmpty()) {
            return new ResponseMessage(ERROR_IN_FIRST_NAME, 400, null, ERROR);
        } else if (user.getLastName().isEmpty()) {
            return new ResponseMessage(ERROR_IN_LAST_NAME, 400, null, ERROR);
        } else if (user.getUsername().isEmpty()) {
            return new ResponseMessage(ERROR_IN_USERNAME, 400, null, ERROR);
        } else if (user.getPassword().isEmpty()) {
            return new ResponseMessage(ERROR_IN_PASSWORD, 400, null, ERROR);
        } else if (user.getPassword().length() <= 6) {
            return new ResponseMessage(ERROR_IN_PASSWORD_LENGTH, 400, null, ERROR);
        } else if (user.getRole() != Role.USER && user.getRole() != Role.ADMIN) {
            return new ResponseMessage(ERROR_IN_USER_ROLE, 400, null, ERROR);
        } else if (user.getEmail().isEmpty()) {
            return new ResponseMessage(ERROR_IN_USER_EMAIL, 400, null, ERROR);
        } else if (!user.getEmail().contains("@")) {
            return new ResponseMessage(ERROR_IN_USER_EMAIL_ID, 400, null, ERROR);
        } else if (user.getContact().isEmpty()) {
            return new ResponseMessage(ERROR_IN_USER_CONTACT, 400, null, ERROR);
        } else if (user.getContact().length() != 10 || (!user.getContact().startsWith("7") && !user.getContact().startsWith("8") && !user.getContact().startsWith("9"))) {
            return new ResponseMessage(ERROR_IN_USER_CONTACT_NUMBER, 400, null, ERROR);
        } else {
            User userNameExisted = userRepository.findByUsername(user.getUsername()).orElse(null);
            User contactExisted = userRepository.findByContact(user.getContact()).orElse(null);
            User emailExisted = userRepository.findByEmail(user.getEmail()).orElse(null);
            if (userNameExisted != null) {
                return new ResponseMessage(USERNAME_ALREADY_EXISTS, 400, null, ERROR);
            } else if (contactExisted != null) {
                return new ResponseMessage(CONTACT_ALREADY_EXISTS, 400, null, ERROR);
            } else if (emailExisted != null) {
                return new ResponseMessage(EMAIL_ID_ALREADY_EXISTS, 400, null, ERROR);
            }
        }
        return null;
    }
}
