package com.raunak.library.review_service.util;

public class ApiError {
    String status;
    String message;
    public ApiError(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
