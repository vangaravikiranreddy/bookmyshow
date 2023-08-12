package com.scaler.bookmyshow.dtos;

import com.scaler.bookmyshow.models.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MovieResponseDto {
    private List<Movie> movies;
}
