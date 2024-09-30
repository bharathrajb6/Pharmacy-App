package com.example.PharmacyApp.repository;

import com.example.PharmacyApp.model.Persistance.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByContact(String contact);
    Optional<User> findByEmail(String email);
}
