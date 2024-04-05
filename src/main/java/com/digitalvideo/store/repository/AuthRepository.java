package com.digitalvideo.store.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.digitalvideo.store.model.User;

public interface AuthRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);
}
