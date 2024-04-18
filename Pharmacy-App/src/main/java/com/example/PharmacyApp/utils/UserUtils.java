package com.example.PharmacyApp.utils;import com.example.PharmacyApp.model.Persistance.Role;import com.example.PharmacyApp.model.Persistance.User;import com.example.PharmacyApp.model.ResponseMessage;import com.example.PharmacyApp.repository.UserRepository;import org.springframework.beans.factory.annotation.Autowired;public class UserUtils {    @Autowired    private static UserRepository userRepository;    public static ResponseMessage validateUserDetails(User user) {        User userNameExisted = userRepository.findByUsername(user.getUsername()).orElse(null);        User contactExisted = userRepository.findByContact(user.getContact()).orElse(null);        User emailExisted = userRepository.findByEmail(user.getEmail()).orElse(null);        if (userNameExisted == null && contactExisted == null && emailExisted == null) {            if (user.getFirstName().isEmpty()) {                return new ResponseMessage("First name is empty", 400, null, "ERROR");            } else if (user.getLastName().isEmpty()) {                return new ResponseMessage("Last name is empty", 400, null, "ERROR");            } else if (user.getUsername().isEmpty()) {                return new ResponseMessage("Username is empty", 400, null, "ERROR");            } else if (user.getPassword().isEmpty()) {                return new ResponseMessage("Password is empty", 400, null, "ERROR");            } else if (user.getPassword().length() <= 6) {                return new ResponseMessage("Password length should be more than 6", 400, null, "ERROR");            } else if (user.getRole() != Role.USER || user.getRole() != Role.ADMIN) {                return new ResponseMessage("Role should be either ADMIn or USER", 400, null, "ERROR");            } else if (!user.getEmail().contains("@")) {                return new ResponseMessage("Invalid email", 400, null, "ERROR");            } else if (user.getContact().isEmpty()) {                return new ResponseMessage("Contact is empty", 400, null, "ERROR");            } else if (user.getContact().length() != 10 || !user.getContact().startsWith("7") || !user.getContact().startsWith("8") || !user.getContact().startsWith("9")) {                return new ResponseMessage("Invalid contact", 400, null, "ERROR");            }            return null;        } else {            return new ResponseMessage("Already exists", 400, null, "ERROR");        }    }}