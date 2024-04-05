package com.digitalvideo.store.controller;

import com.digitalvideo.store.model.Movie;
import com.digitalvideo.store.service.MovieService;
import com.digitalvideo.store.types.SuccessResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping
  public ResponseEntity<SuccessResponse<List<Movie>>> getAllMovies() {
    List<Movie> movies = movieService.getAllMovies();
    SuccessResponse<List<Movie>> response = new SuccessResponse<>(movies);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
