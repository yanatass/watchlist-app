package com.example.watchlist;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenDto {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
