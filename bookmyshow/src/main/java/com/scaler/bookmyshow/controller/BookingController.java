package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dtos.BookMovieRequestDto;
import com.scaler.bookmyshow.dtos.BookMovieResponseDto;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

      public BookMovieResponseDto bookMovie(BookMovieRequestDto request) {
        BookMovieResponseDto response = new BookMovieResponseDto();

          Booking booking;

          try {
              booking = bookingService.bookMovie(
                request.getUserId(),
                request.getShowSeatIds(),
                 request.getShowId()
              );
             response.setResponseStatus(ResponseStatus.SUCCESS);
             response.setBookingId(booking.getId());
             response.setAmount(booking.getAmount());
          } catch (Exception e) {
              response.setResponseStatus(ResponseStatus.FAILURE);
          }

        return response;
      }
}
