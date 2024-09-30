package com.example.PharmacyApp.model.Wrappers;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderWrapper {
    private int orderID;
    private int userID;
    private float price;
    private Date purchased_date;
    private List<MedicationQuantityWrapper> medications;
}
