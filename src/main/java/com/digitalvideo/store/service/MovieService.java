package com.digitalvideo.store.service;

import com.digitalvideo.store.model.Movie;
import com.digitalvideo.store.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;

  public List<Movie> getAllMovies() {
    return movieRepository.findAll();
  }

  public Optional<Movie> getMovieById(String id) {
    return movieRepository.findById(id);
  }
}
