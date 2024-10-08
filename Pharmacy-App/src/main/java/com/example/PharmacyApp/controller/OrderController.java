package com.example.PharmacyApp.controller;

import com.example.PharmacyApp.model.Wrappers.OrderWrapper;
import com.example.PharmacyApp.model.Persistance.Orders;
import com.example.PharmacyApp.model.Responses.ResponseMessage;
import com.example.PharmacyApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
@RestController
@RequestMapping("admin/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("new")
    public ResponseEntity<ResponseMessage> createOrder(@RequestBody Orders order) throws Exception {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @RequestMapping(value = "/getOrder/{orderID}", method = RequestMethod.GET)
    public ResponseEntity<OrderWrapper> getOrderDetails(@PathVariable Integer orderID) {
        return ResponseEntity.ok(orderService.getOrder(orderID));
    }

    @RequestMapping(value = "/getOrders",method = RequestMethod.GET)
    public ResponseEntity<List<OrderWrapper>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @RequestMapping(value = "/getOrdersByUser",method = RequestMethod.GET)
    public ResponseEntity<List<OrderWrapper>> getAllOrdersByUser(@RequestBody String userID){
        return ResponseEntity.ok(orderService.getAllOrdersByUser(userID));
    }

    @RequestMapping(value = "/getOrderByDate",method = RequestMethod.GET)
    public ResponseEntity<List<OrderWrapper>> getOrdersByDate(@RequestBody String date) throws ParseException {
        return ResponseEntity.ok(orderService.getAllOrdersByDate(date));
    }
}
