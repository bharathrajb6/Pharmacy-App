package com.example.PharmacyApp.model.Persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Orders {
    @Id
    private int orderID;
    private int userID;
    private float price;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date purchased_date;

    @OneToMany(mappedBy = "order")
    private List<MedicationQuantity> medicationQuantityList;
}
