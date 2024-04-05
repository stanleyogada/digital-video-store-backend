package com.digitalvideo.store.repository;

import com.digitalvideo.store.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
