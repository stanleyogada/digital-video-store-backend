package com.digitalvideo.store.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.digitalvideo.store.model.User;
import com.digitalvideo.store.repository.AuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthRepository authRepository;

  public Optional<User> authenticate(String email, String password) {
    Optional<User> user = authRepository.findByEmail(email);

    if (!user.isPresent()) {
      return null;
    }

    boolean isPasswordMatch = user.get().getPassword().equals(password);

    if (!isPasswordMatch) {
      return null;
    }

    return user;
  }
}
