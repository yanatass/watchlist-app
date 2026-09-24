package com.example.watchlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(MovieService.class);

    public MovieService(MovieRepository movieRepository, CategoryRepository categoryRepository, UserService userService) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public Movie getMovieById(int id){
        Movie currentMovie = movieRepository.findById(id) .orElseThrow(() -> new MovieNotFound("Movie with id " + id + " not found"));
        User currentUser = userService.getCurrentUser();
        if(currentUser.getId() != currentMovie.getUser().getId()){
            throw new AccessDenied("You are not the owner of this movie");
        }
        return currentMovie;
    }

    public Movie updateMovie(int id, Movie updated){
        Movie movie = getMovieById(id);
        movie.setTitle(updated.getTitle());
        movie.setCategory(updated.getCategory());
        movie.setWatched(updated.isWatched());
        return movieRepository.save(movie);

    }

    public void deleteMovie(int id){
        Movie currentMovie = getMovieById(id);
        logger.info("Movie deleted",  currentMovie.getTitle());
        movieRepository.deleteById(currentMovie.getId());
    }

    public List<Movie> getMoviesByCategory(int categoryId){
        return movieRepository.findByCategoryId(categoryId);
    }

    public Movie createDtoMovie(CreateMovieDto dto){
        Category cat = categoryRepository.findById(dto.getCategoryId()) .orElseThrow(() -> new CategoryNotFound("Category with id " + dto.getCategoryId() + " not found"));
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setCategory(cat);
        movie.setWatched(dto.isWatched());
        User currentUser = userService.getCurrentUser();
        movie.setUser(currentUser);
        logger.info("Создан фильм '{}' пользователем id={}", movie.getTitle(), currentUser.getId());
        return movieRepository.save(movie);
    }

    public MovieResponseDto toResponseDto(Movie movie){
        MovieResponseDto responseDto = new MovieResponseDto();
        responseDto.setTitle(movie.getTitle());
        responseDto.setId(movie.getId());
        responseDto.setWatched(movie.isWatched());
        responseDto.setCategoryName(movie.getCategory().getName());
        return responseDto;
    }

    public List<MovieResponseDto> getAllMoviesResponseDto(Pageable pageable){
        List<MovieResponseDto> dto = new ArrayList<>();
        Page<Movie> movie = movieRepository.findByUserId(userService.getCurrentUser().getId(), pageable);
        List<Movie> currentMovies = movie.getContent();

        for( Movie dto1 : currentMovies){
            dto.add(toResponseDto(dto1));
        }

        return dto;
    }

    public MovieResponseDto toUpdateDto(int id, UpdateMovieDto dto){
        Movie movie = getMovieById(id);
        Category cat = categoryRepository.findById(dto.getCategoryId()) .orElseThrow(() -> new CategoryNotFound("Category with id " + dto.getCategoryId() + " not found"));
        movie.setTitle(dto.getTitle());
        movie.setCategory(cat);
        movie.setWatched(dto.isWatched());
        Movie updatedMovie = movieRepository.save(movie);
        logger.info("Movie updated", updatedMovie.getTitle());
        return toResponseDto(updatedMovie);
    }


}
