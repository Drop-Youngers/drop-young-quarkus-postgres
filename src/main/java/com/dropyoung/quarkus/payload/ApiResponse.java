package com.dropyoung.quarkus.payload;

import lombok.Data;

@Data
public class ApiResponse {

    private boolean success;
    private String message;
    private Object data;

    ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    public static ApiResponse error(String message, Object data) {
        return new ApiResponse(false, message, data);
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(true, message, null);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }


}

