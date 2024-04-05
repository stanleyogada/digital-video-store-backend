package com.digitalvideo.store.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.digitalvideo.store.model.User;
import com.digitalvideo.store.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public Optional<User> getCustomerById(String id) {
    return customerRepository.findById(id);
  }

  public Optional<User> getCustomerByEmail(String email) {
    return customerRepository.findByEmail(email);
  }
}
