package com.digitalvideo.store.controller;

import com.digitalvideo.store.model.Movie;
import com.digitalvideo.store.service.MovieService;
import com.digitalvideo.store.types.ErrorResponse;
import com.digitalvideo.store.types.SuccessResponse;

import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
@Validated
@ValidateOnExecution
public class MovieController {

  private final MovieService movieService;

  @GetMapping
  public ResponseEntity<SuccessResponse<List<Movie>>> getAllMovies(
      @RequestParam(name = "is_tv_show", required = false) Boolean isTvShow,
      @RequestParam(name = "is_featured", required = false) Boolean isFeatured,
      @RequestParam(name = "title", required = false) String title) {

    if (title != null) {
      List<Movie> movies = movieService.searchMoviesByTitle(title);
      SuccessResponse<List<Movie>> response = new SuccessResponse<>(movies);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    if (isTvShow != null && isFeatured == null) {
      List<Movie> movies = movieService.getAllMovies(isTvShow);
      SuccessResponse<List<Movie>> response = new SuccessResponse<>(movies);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    if (isTvShow == null && isFeatured == null) {
      List<Movie> movies = movieService.getAllMovies(false);
      SuccessResponse<List<Movie>> response = new SuccessResponse<>(movies);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    List<Movie> movies = movieService.getAllMovies(isFeatured, isTvShow != null && isTvShow);
    SuccessResponse<List<Movie>> response = new SuccessResponse<>(movies);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getMovieById(@PathVariable String id) {
    Optional<Movie> movie = movieService.getMovieById(id);

    if (movie.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie or TV Show not found");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.NOT_FOUND);
    }

    SuccessResponse<Optional<Movie>> response = new SuccessResponse<>(movie);
    return new ResponseEntity<SuccessResponse<Optional<Movie>>>(response, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createMovie(@Valid @RequestBody Movie movie) {
    try {
      Movie createdMovie = movieService.createMovie(movie);
      SuccessResponse<Movie> response = new SuccessResponse<>(createdMovie);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      ErrorResponse<String> response = new ErrorResponse<>("Failed to create movie: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
