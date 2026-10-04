package com.example.watchlist.movie;

public class MovieNotFound extends RuntimeException {
    public MovieNotFound(String message) {
        super(message);
    }
}
