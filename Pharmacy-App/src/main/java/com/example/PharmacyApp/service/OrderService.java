package com.example.PharmacyApp.service;import com.example.PharmacyApp.model.API.MedicationQuantityWrapper;import com.example.PharmacyApp.model.API.OrderWrapper;import com.example.PharmacyApp.model.Persistance.MedicationQuantity;import com.example.PharmacyApp.model.Persistance.Medication;import com.example.PharmacyApp.model.Persistance.Orders;import com.example.PharmacyApp.model.ResponseMessage;import com.example.PharmacyApp.repository.MedicationQuantityRepository;import com.example.PharmacyApp.repository.MedicationRepository;import com.example.PharmacyApp.repository.OrderRepository;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import java.util.ArrayList;import java.util.List;import static com.example.PharmacyApp.utils.CommonUtils.createResponseMessage;import static com.example.PharmacyApp.utils.CommonUtils.generateRandomNumber;@Servicepublic class OrderService {    @Autowired    private OrderRepository orderRepository;    @Autowired    private MedicationService medicationService;    @Autowired    private MedicationRepository medicationRepository;    @Autowired    private MedicationQuantityRepository medicationQuantityRepository;    public ResponseMessage createOrder(Orders request) throws Exception {        Orders orders = new Orders();        orders.setOrderID(generateRandomNumber(5));        orders.setUserID(request.getUserID());        orders.setPurchased_date(request.getPurchased_date());        float amount = calculateTotalPrice(request.getMedicationQuantityList());        if (amount > 0) {            orders.setPrice(amount);            orderRepository.save(orders);            request.getMedicationQuantityList().forEach(medicationQuantity -> {                try {                    medicationQuantity.setId(generateRandomNumber(3));                    medicationQuantity.setOrder(orders);                } catch (Exception e) {                    throw new RuntimeException(e);                }            });            medicationQuantityRepository.saveAll(request.getMedicationQuantityList());            return createResponseMessage("Order is created successfully", 200, null, "SUCCESS");        } else {            return createResponseMessage("Stock is empty", 400, null, "ERROR");        }    }    public OrderWrapper getOrder(Integer orderID) {        Orders orders = orderRepository.findById(orderID).orElse(null);        if (orders != null) {            List<MedicationQuantity> medicationQuantityList = medicationQuantityRepository.findAllMedicationQuantityByID(orders.getOrderID());            final List<MedicationQuantityWrapper> medicationQuantityWrapperList = generateMedicationWrapper(medicationQuantityList);            return generateOrderWrapper(orders, medicationQuantityWrapperList);        } else {            return null;        }    }    private float calculateTotalPrice(List<MedicationQuantity> medicationList) {        float amount = 0;        for (MedicationQuantity medicationQuantity : medicationList) {            Medication medication = medicationService.getMedicationDetailsById(medicationQuantity.getMedicationid());            if (medicationQuantity.getQuantity() <= medication.getStockQuantity()) {                amount += medication.getPrice() * medicationQuantity.getQuantity();                medication.setStockQuantity(medication.getStockQuantity() - medicationQuantity.getQuantity());                medicationRepository.save(medication);            } else {                return 0;            }        }        return amount;    }    private OrderWrapper generateOrderWrapper(Orders orders, List<MedicationQuantityWrapper> medicationQuantityWrapperList) {        OrderWrapper orderWrapper = new OrderWrapper();        orderWrapper.setOrderID(orders.getOrderID());        orderWrapper.setUserID(orders.getUserID());        orderWrapper.setPurchased_date(orders.getPurchased_date());        orderWrapper.setPrice(orders.getPrice());        orderWrapper.setMedications(medicationQuantityWrapperList);        return orderWrapper;    }    private List<MedicationQuantityWrapper> generateMedicationWrapper(List<MedicationQuantity> medicationQuantityList) {        List<MedicationQuantityWrapper> medicationQuantityWrapperList = new ArrayList<>();        for (MedicationQuantity medicationQuantity : medicationQuantityList) {            MedicationQuantityWrapper medicationQuantityWrapper = new MedicationQuantityWrapper();            medicationQuantityWrapper.setMedicationID(medicationQuantity.getMedicationid());            medicationQuantityWrapper.setMedicationName(medicationQuantity.getMedication_name());            medicationQuantityWrapper.setQuantity(medicationQuantity.getQuantity());            medicationQuantityWrapperList.add(medicationQuantityWrapper);        }        return medicationQuantityWrapperList;    }}