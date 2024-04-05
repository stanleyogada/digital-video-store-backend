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
import org.springframework.web.bind.annotation.PutMapping;

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

    String name = movie.getName();

    List<Movie> movies = movieService.searchMoviesByTitle(name);

    if (movies.size() > 0) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie already exists");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    String synopsis = movie.getSynopsis();
    double priceRent = movie.getPriceRent();
    double priceBuy = movie.getPriceBuy();
    String posterImg = movie.getPosterImg();
    String posterLargeImg = movie.getPosterLargeImg();

    if (name == null || name.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie name is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (synopsis == null || synopsis.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie synopsis is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (priceRent <= 2 || priceBuy <= 2) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie price must be greater than 2");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (posterImg == null || posterImg.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie poster image is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (posterLargeImg == null || posterLargeImg.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie large poster image is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    if (!movie.isTvShow()) {
      ErrorResponse<String> response = new ErrorResponse<>("isTvShow field is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    if (!movie.isFeatured()) {
      ErrorResponse<String> response = new ErrorResponse<>("isFeatured field is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    try {
      Movie createdMovie = movieService.createMovie(movie);
      SuccessResponse<Movie> response = new SuccessResponse<>(createdMovie);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      ErrorResponse<String> response = new ErrorResponse<>("Failed to create movie: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable String id, @RequestBody Movie movie) {
    Optional<Movie> existingMovie = movieService.getMovieById(id);

    if (existingMovie.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie or TV Show not found");
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    String name = movie.getName();

    List<Movie> movies = movieService.searchMoviesByTitle(name);
    if (movies.size() > 0 && !movies.get(0).getName().equals(name)) {
      ErrorResponse<String> response = new ErrorResponse<>(
          "Change the name to something else a movie has this already");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    String synopsis = movie.getSynopsis();
    double priceRent = movie.getPriceRent();
    double priceBuy = movie.getPriceBuy();
    String posterImg = movie.getPosterImg();
    String posterLargeImg = movie.getPosterLargeImg();

    if (name == null || name.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie name is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (synopsis == null || synopsis.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie synopsis is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (priceRent <= 2 || priceBuy <= 2) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie price must be greater than 2");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (posterImg == null || posterImg.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie poster image is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (posterLargeImg == null || posterLargeImg.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie large poster image is required");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    if (name.length() < 2) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie name must be at least 2 characters long");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (synopsis.length() < 2) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie synopsis must be at least 2 characters long");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (posterImg.length() < 6) {
      ErrorResponse<String> response = new ErrorResponse<>("Movie poster image must be at least 6 characters long");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    if (posterLargeImg.length() < 6) {
      ErrorResponse<String> response = new ErrorResponse<>(
          "Movie large poster image must be at least 6 characters long");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // if (!movie.isTvShow()) {
    // ErrorResponse<String> response = new ErrorResponse<>("isTvShow field is
    // required");
    // return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    // }
    // if (!movie.isFeatured()) {
    // ErrorResponse<String> response = new ErrorResponse<>("isFeatured field is
    // required");
    // return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    // }

    Movie updatedMovie = movieService.updateMovie(id, movie);

    SuccessResponse<Movie> response = new SuccessResponse<>(updatedMovie);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
