package com.scaler.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ShowResponse {
    private Long showId;
    private Date startTime;
    private Date endTime;
    private String screenName;
    private String theaterName;
    private String regionName;
    private List<SeatResponse> seats;
}