package com.digitalvideo.store.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "movies")
public class Movie {

  @Id
  private String id;
  private String name;
  private String synopsis;
  private double priceRent;
  private double priceBuy;
  private String posterImg;
  private String posterLargeImg;
  private boolean isTvShow;
  private boolean isFeatured;
}
