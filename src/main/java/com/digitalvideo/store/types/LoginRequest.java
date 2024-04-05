package com.digitalvideo.store.types;

import lombok.Data;

@Data
public class LoginRequest {
  private String email;
  private String password;
}
