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

  public List<Movie> searchMoviesByTitle(String name) {
    return movieRepository.findByNameContainingIgnoreCase(name);
  }

  public Optional<Movie> getMovieById(String id) {
    return movieRepository.findById(id);
  }

  public Movie createMovie(Movie movie) {
    Movie newMovie = new Movie();

    newMovie.setName(movie.getName());
    newMovie.setSynopsis(movie.getSynopsis());
    newMovie.setPriceRent(movie.getPriceRent());
    newMovie.setPriceBuy(movie.getPriceBuy());
    newMovie.setPosterImg(movie.getPosterImg());
    newMovie.setPosterLargeImg(movie.getPosterLargeImg());
    newMovie.setIsTvShow(movie.isTvShow());
    newMovie.setIsFeatured(movie.isFeatured());

    return movieRepository.save(newMovie);
  }

  public Movie updateMovie(String id, Movie movie) {
    Optional<Movie> existingMovie = this.getMovieById(id);

    if (existingMovie.isEmpty()) {
      return null;
    }

    Movie updatedMovie = existingMovie.get();

    updatedMovie.setName(movie.getName());
    updatedMovie.setSynopsis(movie.getSynopsis());
    updatedMovie.setPriceRent(movie.getPriceRent());
    updatedMovie.setPriceBuy(movie.getPriceBuy());
    updatedMovie.setPosterImg(movie.getPosterImg());
    updatedMovie.setPosterLargeImg(movie.getPosterLargeImg());
    updatedMovie.setIsTvShow(movie.isTvShow());
    updatedMovie.setIsFeatured(movie.isFeatured());

    return movieRepository.save(updatedMovie);
  }

  public String deleteMovie(String id) {
    Optional<Movie> movie = this.getMovieById(id);

    if (movie.isEmpty()) {
      return "fail";
    }

    movieRepository.deleteById(id);
    return "success";
  }
}
