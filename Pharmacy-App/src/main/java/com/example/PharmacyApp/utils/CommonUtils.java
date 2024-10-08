package com.example.PharmacyApp.utils;

import com.example.PharmacyApp.model.Responses.ResponseMessage;

import java.util.Random;

import static com.example.PharmacyApp.messages.CommonInformation.*;

public class CommonUtils {
    public static int generateRandomNumber(int length) throws Exception {
        if (length < 3 || length > 16) {
            throw new Exception(ID_LENGTH_EXCEPTION);
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // Generate a random digit from 0 to 9
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return Integer.parseInt(sb.toString());
    }

    public static ResponseMessage createResponseMessage(String message, int statusCode, String description, String type) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage(message);
        responseMessage.setDescription(description);
        responseMessage.setType(type);
        responseMessage.setStatusCode(statusCode);
        return responseMessage;
    }
}
