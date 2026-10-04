package com.example.watchlist.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer>{
    List<Movie> findByCategoryId(int categoryId);
    Page<Movie> findByUserIdAndDeletedFalse(int userId, Pageable pageable);
}
