package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dtos.BookMovieRequestDto;
import com.scaler.bookmyshow.dtos.BookMovieResponseDto;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class BookingController {

    private static final Logger logger = Logger.getLogger(ShowController.class.getName());

    private BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

     @PostMapping("/book")
     @ResponseBody
      public BookMovieResponseDto bookMovie(@RequestBody BookMovieRequestDto request) {
         logger.log(Level.INFO, "Start of bookMovie : " );
        BookMovieResponseDto response = new BookMovieResponseDto();

          Booking booking;

          try {
              booking = bookingService.bookMovie(
                request.getUserId(),
                request.getSeatIds(),
                 request.getShowId()
              );
             response.setResponseStatus(ResponseStatus.SUCCESS);
             response.setBookingId(booking.getId());
             response.setBookedAt(booking.getBookedAt());
             response.setMovieName(booking.getShow().getMovie().getName());
             response.setMovieImageUrl(booking.getShow().getMovie().getImageUrl());
             response.setScreenNumber(booking.getShow().getScreen().getName());
             response.setTheaterName(booking.getShow().getScreen().getTheater().getName());
             response.setLocatedAt(booking.getShow().getScreen().getTheater().getRegion().getName());
             response.setAmount(booking.getAmount());
          } catch (Exception e) {
               logger.log(Level.INFO,e.getMessage());
              response.setResponseStatus(ResponseStatus.FAILURE);
          }
         logger.log(Level.INFO, "end of bookMovie : " );
        return response;
      }
}
