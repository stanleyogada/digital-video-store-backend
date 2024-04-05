package com.digitalvideo.store.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalvideo.store.model.User;
import com.digitalvideo.store.service.CustomerService;
import com.digitalvideo.store.types.ErrorResponse;
import com.digitalvideo.store.types.SuccessResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private static final String ERROR_NOT_FOUND = "Customer not found";

  @GetMapping("/{id}")
  public ResponseEntity<?> getCustomerById(@PathVariable String id) {
    Optional<User> user = customerService.getCustomerById(id);

    if (user.isEmpty()) {
      ErrorResponse<String> response = new ErrorResponse<>(ERROR_NOT_FOUND);
      return new ResponseEntity<ErrorResponse<String>>(response, HttpStatus.NOT_FOUND);
    }

    SuccessResponse<Optional<User>> response = new SuccessResponse<>(user);
    return new ResponseEntity<SuccessResponse<Optional<User>>>(response, HttpStatus.OK);
  }
}
