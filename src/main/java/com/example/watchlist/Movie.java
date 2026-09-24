package com.example.watchlist;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Write down all information!!!!!")
    private String title;

    @ManyToOne
    private Category category;

    private boolean watched;

    @ManyToOne
    private User user;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public boolean isWatched() {
        return watched;
    }
    public void setWatched(boolean watched) {
        this.watched = watched;
    }
    public User getUser() {
        return user;

    }
    public void setUser(User user) {
        this.user = user;
    }


}
