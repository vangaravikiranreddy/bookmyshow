package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.Booking;

import java.util.List;

public interface BookingService {
    Booking bookMovie(Long userId, List<Long> seatId, Long showId);
}
