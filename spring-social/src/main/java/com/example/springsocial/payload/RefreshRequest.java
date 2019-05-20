package com.example.springsocial.payload;

import javax.validation.constraints.NotBlank;

public class RefreshRequest {

    @NotBlank(message = "Refresh token cannot be blank")
    private String refreshToken;

    public RefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshRequest() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
