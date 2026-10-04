package com.example.watchlist.movie;
import com.example.watchlist.category.Category;
import com.example.watchlist.category.CategoryNotFound;
import com.example.watchlist.category.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.watchlist.user.User;
import com.example.watchlist.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, CategoryRepository categoryRepository, UserService userService, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.movieMapper = movieMapper;
    }

    public Movie getMovieById(int id){
        Movie currentMovie = movieRepository.findById(id) .orElseThrow(() -> new MovieNotFound("Movie with id " + id + " not found"));
        if(currentMovie.isDeleted()){
            throw new MovieNotFound("Movie with id " + id + " not found");
        }
        User currentUser = userService.getCurrentUser();
        if(currentUser.getId() != currentMovie.getUser().getId() ){
            throw new AccessDenied("You are not the owner of this movie");
        }
        return currentMovie;
    }

    public void deleteMovie(int id){
        Movie currentMovie = getMovieById(id);
        logger.info("Movie deleted: {}",  currentMovie.getTitle());
        currentMovie.setDeleted(true);
        movieRepository.save(currentMovie);
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

//    public MovieResponseDto toResponseDto(Movie movie){
//        MovieResponseDto responseDto = new MovieResponseDto();
//        responseDto.setTitle(movie.getTitle());
//        responseDto.setId(movie.getId());
//        responseDto.setWatched(movie.isWatched());
//        responseDto.setCategoryName(movie.getCategory().getName());
//        return responseDto;
//    }

    public List<MovieResponseDto> getAllMoviesResponseDto(Pageable pageable){
        List<MovieResponseDto> dto = new ArrayList<>();
        Page<Movie> movie = movieRepository.findByUserIdAndDeletedFalse(userService.getCurrentUser().getId(), pageable);
        List<Movie> currentMovies = movie.getContent();

        for( Movie dto1 : currentMovies){
            dto.add(movieMapper.toResponseDto(dto1));
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
        logger.info("Movie updated: {}", updatedMovie.getTitle());
        return movieMapper.toResponseDto(updatedMovie);
    }


}
