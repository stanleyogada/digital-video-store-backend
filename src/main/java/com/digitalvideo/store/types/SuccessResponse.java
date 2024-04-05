package com.digitalvideo.store.types;

import lombok.Data;

@Data
public class SuccessResponse<T> {
  private String status;
  private T data;

  public SuccessResponse(T data) {
    this.status = "success";
    this.data = data;
  }
}
