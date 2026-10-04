package com.example.watchlist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
 class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private MovieService movieService;

    @Test
    void getMovie_whenHaveOwner(){
        User user = new User();
        user.setId(1);
        Movie movie = new Movie();
        movie.setId(1);
        movie.setUser(user);
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Movie example = movieService.getMovieById(1);
        Assertions.assertEquals(movie, example);


    }
    @Test
    void getMovie_whenMovieNotFound(){
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(MovieNotFound.class, () -> {
            movieService.getMovieById(1);
        });
    }

    @Test
    void getMovie_whenNotOwner(){
        User user = new User();
        User currentUser = new User();
        currentUser.setId(2);
        user.setId(1);
        Movie movie = new Movie();
        movie.setId(1);
        movie.setUser(user);
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Mockito.when(userService.getCurrentUser()).thenReturn(currentUser);

        Assertions.assertThrows(AccessDenied.class, () -> {movieService.getMovieById(1);});

    }
    @Test
    void deleteMovie_whenOwner(){
        User user = new User();
        user.setId(1);
        Movie movie = new Movie();
        movie.setId(1);
        movie.setUser(user);
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        movieService.deleteMovie(1);
        Mockito.verify(movieRepository).save(movie);

        // Act: вызови movieService.deleteMovie(1)

        // Assert: Mockito.verify(movieRepository).deleteById(1)
    }
}
