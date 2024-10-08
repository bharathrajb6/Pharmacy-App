package com.example.PharmacyApp.model.Responses;

import lombok.Data;

@Data
public class ResponseMessage {
    private String message;
    private int statusCode;
    private String description;
    private String type;

    public ResponseMessage(String message, int statusCode, String description, String type) {
        this.message = message;
        this.statusCode = statusCode;
        this.description = description;
        this.type = type;
    }

    public ResponseMessage() {
    }
}
