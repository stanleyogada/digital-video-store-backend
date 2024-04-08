package com.digitalvideo.store.repository;

import com.digitalvideo.store.model.Movie;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
  List<Movie> findByIsFeaturedAndIsTvShow(boolean isFeatured, boolean isTvShow);

  List<Movie> findByIsTvShow(boolean isTvShow);

  List<Movie> findByNameContainingIgnoreCase(String name);

  Optional<Movie> findByName(String name);
}
