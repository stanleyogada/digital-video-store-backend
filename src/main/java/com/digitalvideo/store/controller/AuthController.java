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
import com.digitalvideo.store.service.CustomerService;
import com.digitalvideo.store.types.ErrorResponse;
import com.digitalvideo.store.types.LoginRequest;
import com.digitalvideo.store.types.RegistrationRequest;
import com.digitalvideo.store.types.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final CustomerService customerService;

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

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {

    User user = new User();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());

    if (customerService.getCustomerByEmail(user.getEmail()).isPresent()) {
      ErrorResponse<String> response = new ErrorResponse<>("Email is already in use");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (user.getEmail() == null || user.getEmail().isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Email is required");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (user.getPassword() == null || user.getPassword().isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Password is required");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("First name is required");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (user.getLastName() == null || user.getLastName().isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>("Last name is required");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (user.getFirstName().length() < 3) {
      ErrorResponse<String> response = new ErrorResponse<>("First name must be at least 3 characters");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (user.getLastName().length() < 3) {
      ErrorResponse<String> response = new ErrorResponse<>("Last name must be at least 3 characters");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (user.getPassword().length() < 6) {
      ErrorResponse<String> response = new ErrorResponse<>("Password must be at least 6 characters");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    if (user.getEmail().length() < 6) {
      ErrorResponse<String> response = new ErrorResponse<>("Email must be at least 6 characters");
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.BAD_REQUEST);
    }

    User newUser = authService.registerUser(user);

    SuccessResponse<User> response = new SuccessResponse<>(newUser);
    return new ResponseEntity<SuccessResponse<User>>(response, HttpStatus.CREATED);
  }
}
