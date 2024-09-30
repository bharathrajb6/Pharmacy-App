package com.example.PharmacyApp.model.Persistance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "medication_quantity")
public class MedicationQuantity {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "medicationid")
    private int medicationid;

    @Column(name = "medication_name")
    private String medication_name;

    @Column(name = "quantity")
    private int quantity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
}
