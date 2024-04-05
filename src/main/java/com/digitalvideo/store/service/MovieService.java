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

  public List<Movie> getAllMovies(boolean isFeatured, boolean isTvShow) {
    return movieRepository.findByIsFeaturedAndIsTvShow(isFeatured, isTvShow);
  }

  public List<Movie> getAllMovies(boolean isTvShow) {
    return movieRepository.findByIsTvShow(isTvShow);
  }

  public Optional<Movie> getMovieById(String id) {
    return movieRepository.findById(id);
  }
}
