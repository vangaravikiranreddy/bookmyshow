package com.scaler.bookmyshow.dtos;

import com.scaler.bookmyshow.models.Theater;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookMovieResponseDto {
    private ResponseStatus responseStatus;
    private Long bookingId;
    private Date bookedAt;
    private String movieName;
    private String screenNumber;
    private String TheaterName;
    private String locatedAt;
    private  int amount;
}
