package com.lifestyle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String message;
    private String token;

    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() { return message; }
    public String getToken() { return token; }
}
