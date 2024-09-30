package com.example.PharmacyApp.model.Wrappers;

import lombok.Data;

@Data
public class MedicationQuantityWrapper {
    private int medicationID;
    private String medicationName;
    private int quantity;
}
