package com.digitalvideo.store.types;

import lombok.Data;

@Data
public class ErrorResponse<T> {
  private String status;
  private T data;

  public ErrorResponse(T data) {
    this.status = "fail";
    this.data = data;
  }

  public ErrorResponse(String status, T data) {
    this.status = status;
    this.data = data;
  }
}
