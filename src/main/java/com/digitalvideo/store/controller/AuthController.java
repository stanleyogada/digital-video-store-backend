package com.digitalvideo.store.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalvideo.store.model.User;
import com.digitalvideo.store.service.AuthService;
import com.digitalvideo.store.types.ErrorResponse;
import com.digitalvideo.store.types.LoginRequest;
import com.digitalvideo.store.types.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

    if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Email is required");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Password is required");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    Optional<User> user = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

    if (user == null) {
      ErrorResponse<String> response = new ErrorResponse<>("Invalid credentials");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.UNAUTHORIZED);
    }

    SuccessResponse<Optional<User>> response = new SuccessResponse<>(user);
    return new ResponseEntity<SuccessResponse<Optional<User>>>(response, HttpStatus.OK);
  }

  // @PostMapping("/register")
  // public ResponseEntity<?> register(@RequestBody User user) {
  // String status = authService.register(user);
  // }
}
