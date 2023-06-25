package com.scaler.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
  private String name;

  @OneToMany
  private List<Seat> seats;

  @Enumerated(EnumType.ORDINAL)
  @ElementCollection             // create a mapping table -> Screen,features
  private List<Feature> features;
}
