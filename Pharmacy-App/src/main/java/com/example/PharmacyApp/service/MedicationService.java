package com.example.PharmacyApp.service;

import com.example.PharmacyApp.model.Persistance.Medication;
import com.example.PharmacyApp.model.Responses.ResponseMessage;
import com.example.PharmacyApp.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.PharmacyApp.utils.CommonUtils.generateRandomNumber;
import static com.example.PharmacyApp.utils.CommonUtils.createResponseMessage;
import static com.example.PharmacyApp.messages.MedicationInformation.*;
import static com.example.PharmacyApp.messages.CommonInformation.*;

@Service
public class MedicationService {
    @Autowired
    private MedicationRepository medicationRepository;

    public ResponseMessage addMedication(Medication medication) throws Exception {
        medication.setMedicationID(generateRandomNumber(7));
        medicationRepository.save(medication);
        return createResponseMessage(MEDICATION_IS_ADDED_SUCCESSFUL, 200, null, INFO);
    }

    public Medication getMedicationDetailsById(Integer id) {
        return medicationRepository.findById(id).orElseThrow();
    }

    public List<Medication> getAllMedicationDetails() {
        return medicationRepository.findAll();
    }

    public Medication getMedicationDetailsByName(String name) {
        return medicationRepository.findByName(name);
    }

    public ResponseMessage deleteMedication(Integer medicationID) {
        Medication medication = medicationRepository.findById(medicationID).orElse(null);
        if (medication != null) {
            medicationRepository.delete(medication);
            return createResponseMessage(MEDICATION_IS_DELETED_SUCCESSFUL, 200, null, INFO);
        } else {
            return createResponseMessage(MEDICATION_IS_NOT_FOUND, 404, null, ERROR);
        }
    }

    public ResponseMessage updateMedicationInfo(Medication medication) {
        Medication medicationStoredInDB = medicationRepository.findById(medication.getMedicationID()).orElse(null);
        if (medicationStoredInDB != null) {
            medicationStoredInDB.setName(medication.getName());
            medicationStoredInDB.setDescription(medication.getDescription());
            medicationStoredInDB.setPrice(medication.getPrice());
            medicationStoredInDB.setStockQuantity(medication.getStockQuantity());
            medicationStoredInDB.setManufacturer(medication.getManufacturer());
            medicationStoredInDB.setExpiryDate(medication.getExpiryDate());
            medicationRepository.save(medicationStoredInDB);
            return createResponseMessage(MEDICATION_IS_UPDATED_SUCCESSFUL, 200, null, INFO);
        } else {
            return createResponseMessage(MEDICATION_IS_NOT_FOUND, 404, null, ERROR);
        }
    }
}
