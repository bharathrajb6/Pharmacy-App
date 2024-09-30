package com.example.PharmacyApp.repository;

import com.example.PharmacyApp.model.Persistance.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findOrderDetailsByUserID(Integer userID);
    @Query("""
            select o from Orders o where o.purchased_date=:date
            """)
    List<Orders> findOrderDetailsByDate(Date date);
}
