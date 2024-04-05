package com.digitalvideo.store.types;

import lombok.Data;

@Data
public class ErrorResponse<T> {
  private String status;
  private T message;

  public ErrorResponse(T message) {
    this.status = "fail";
    this.message = message;
  }

  public ErrorResponse(String status, T message) {
    this.status = status;
    this.message = message;
  }
}
