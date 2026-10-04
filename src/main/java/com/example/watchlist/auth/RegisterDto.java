package com.example.watchlist.auth;

import jakarta.validation.constraints.NotBlank;

public class RegisterDto {
    @NotBlank(message = "{user.username.required}")
    private String username;
    @NotBlank(message = "{user.password.required}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
