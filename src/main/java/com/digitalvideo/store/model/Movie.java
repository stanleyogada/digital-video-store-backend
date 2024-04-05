package com.digitalvideo.store.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;

@Data
@Document(collection = "movies")
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class Movie {
  @Id
  private String id;

  @NotNull
  private String name;
  @NotNull
  private String synopsis;

  @NotNull
  private double priceRent;
  @NotNull
  private double priceBuy;

  @NotNull
  private String posterImg;
  @NotNull
  private String posterLargeImg;

  @NotNull
  private boolean isTvShow;
  @NotNull
  private boolean isFeatured;

  public void setIsTvShow(boolean isTvShow) {
    this.isTvShow = isTvShow;
  }

  public void setIsFeatured(boolean isFeatured) {
    this.isFeatured = isFeatured;
  }
}
