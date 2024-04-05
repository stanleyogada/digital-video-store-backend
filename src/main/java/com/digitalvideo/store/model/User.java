package com.digitalvideo.store.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Document(collection = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class User {
  @Id
  private String id;

  private String firstName;
  private String lastName;
  private String email;

  @JsonIgnore
  private String password;
}
