package com.example.PharmacyApp.repository;import com.example.PharmacyApp.model.Medication;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;@Repositorypublic interface MedicationRepository extends JpaRepository<Medication,Integer> {    Medication findByName(String name);}