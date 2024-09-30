package com.example.PharmacyApp.model.Wrappers;

import com.example.PharmacyApp.model.Persistance.Role;
import lombok.Data;

@Data
public class UserWrapper {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private Role role;
}
