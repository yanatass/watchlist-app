package com.example.watchlist;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping
    public List<MovieResponseDto> getMoviesAsDto(Pageable pageable) {
        return movieService.getAllMoviesResponseDto(pageable);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable int id){
        return movieService.getMovieById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable int id){
        movieService.deleteMovie(id);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<Movie> getMovieByCategory(@PathVariable int categoryId){
        return movieService.getMoviesByCategory(categoryId);
    }

    @PostMapping
    public Movie createDtoMovie(@Valid @RequestBody CreateMovieDto createMovieDto){
        return movieService.createDtoMovie(createMovieDto);
    }

    @PutMapping("/{id}")
    public MovieResponseDto updateDtoMovie(@PathVariable int id, @Valid @RequestBody UpdateMovieDto updateMovieDto){
        return movieService.toUpdateDto(id, updateMovieDto);
    }




}

