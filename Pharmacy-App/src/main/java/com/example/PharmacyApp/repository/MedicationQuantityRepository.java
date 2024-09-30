package com.example.PharmacyApp.repository;

import com.example.PharmacyApp.model.Persistance.MedicationQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicationQuantityRepository extends JpaRepository<MedicationQuantity,Integer> {
    @Query("""
            select m from MedicationQuantity m inner join Orders o on m.order.orderID=o.orderID where m.order.orderID=:orderID
            """)
    List<MedicationQuantity> findAllMedicationQuantityByID(Integer orderID);
}
