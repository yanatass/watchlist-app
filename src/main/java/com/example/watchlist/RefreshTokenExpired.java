package com.example.watchlist;

public class RefreshTokenExpired extends RuntimeException {
    public RefreshTokenExpired(String message) {
        super(message);
    }
}
