package com.example.watchlist;

import jakarta.validation.constraints.NotBlank;

public class UpdateMovieDto {
    @NotBlank
    private String title;
    private int categoryId;
    private boolean watched;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
