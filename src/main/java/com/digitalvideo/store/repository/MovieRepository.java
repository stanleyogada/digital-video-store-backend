package com.digitalvideo.store.repository;

import com.digitalvideo.store.model.Movie;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
  List<Movie> findByIsFeaturedAndIsTvShow(boolean isFeatured, boolean isTvShow);

  List<Movie> findByIsTvShow(boolean isTvShow);
}
