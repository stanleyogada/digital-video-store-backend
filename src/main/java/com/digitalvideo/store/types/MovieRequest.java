package com.digitalvideo.store.types;

import lombok.Data;

@Data
public class MovieRequest {
  private String name;
  private String synopsis;
  private double priceRent;
  private double priceBuy;
  private String posterImg;
  private String posterLargeImg;
  private boolean isTvShow;
  private boolean isFeatured;
}
