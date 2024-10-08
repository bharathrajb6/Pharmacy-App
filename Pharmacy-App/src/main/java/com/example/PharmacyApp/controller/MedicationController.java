package com.example.PharmacyApp.controller;

import com.example.PharmacyApp.model.Persistance.Medication;
import com.example.PharmacyApp.model.Responses.ResponseMessage;
import com.example.PharmacyApp.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/medication")
public class MedicationController {
    @Autowired
    private MedicationService medicationService;

    @PostMapping("add")
    public ResponseEntity<ResponseMessage> addMedication(@RequestBody Medication medication) throws Exception {
        return ResponseEntity.ok(medicationService.addMedication(medication));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Medication> getMedication(@PathVariable Integer id) {
        return ResponseEntity.ok(medicationService.getMedicationDetailsById(id));
    }

    @GetMapping("get")
    public ResponseEntity<List<Medication>> getAllMedication() {
        return ResponseEntity.ok(medicationService.getAllMedicationDetails());
    }

    @RequestMapping(value = "/deleteMedication/{medicationID}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteMedication(@PathVariable Integer medicationID) {
        return ResponseEntity.ok(medicationService.deleteMedication(medicationID));
    }

    @RequestMapping(value = "/updateMedication", method = RequestMethod.PUT)
    public ResponseEntity<ResponseMessage> updateMedication(@RequestBody Medication medication) {
        return ResponseEntity.ok(medicationService.updateMedicationInfo(medication));
    }
}
