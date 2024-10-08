package com.example.PharmacyApp.model.Persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "medication")
@Data
public class Medication {
    @Id
    private int medicationID;
    private String name;
    private String description;
    private String manufacturer;
    private int price;
    private int stockQuantity;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date expiryDate;
}
