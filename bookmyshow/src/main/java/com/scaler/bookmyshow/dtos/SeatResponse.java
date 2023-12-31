package com.scaler.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatResponse {
    private long seatId;
    private int rowVal;
    private int colVal;
    private String seatNumber;
    private String seatName;
}