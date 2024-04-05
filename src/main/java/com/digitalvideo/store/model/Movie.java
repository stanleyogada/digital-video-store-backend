package com.digitalvideo.store.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Document(collection = "movies")
public class Movie {
  @Id
  private String id;

  private String name;
  private String synopsis;

  @JsonProperty("price_rent")
  private double priceRent;
  @JsonProperty("price_buy")
  private double priceBuy;

  @JsonProperty("poster_img")
  private String posterImg;
  @JsonProperty("poster_large_img")
  private String posterLargeImg;

  @JsonProperty("is_tv_show")
  private boolean isTvShow;
  @JsonProperty("is_featured")
  private boolean isFeatured;
}
