package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.*;

import com.scaler.bookmyshow.repositories.BookingRepository;
import com.scaler.bookmyshow.repositories.ShowRepository;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;

    private ShowRepository showRepository;

    private ShowSeatRepository showSeatRepository;

    private UserRepository userRepository;

    private PriceCalculatorImpl priceCalculator;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, UserRepository userRepository, PriceCalculatorImpl priceCalculator) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.priceCalculator = priceCalculator;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> seatId, Long showId) {
        //1.Get user with that userId
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User bookedBy = userOptional.get();

        //2. Get show with that showId
        Optional<Show> showOptional = showRepository.findById(showId);

        if (showOptional.isEmpty()) {
            throw new RuntimeException();
        }

        Show bookedShow = showOptional.get();



        // ------------TAKE LOCK---------
        // 3.Get ShowSeat with that seatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatId);

        // 4.Check if all seats are available
        // 5. If no, throw error
      /*  for (ShowSeat showSeat : showSeats) {

            java.util.Date blockedAt = showSeat.getBlockedAt();
            Instant blockedInstant = blockedAt.toInstant();
            Instant currentInstant = Instant.now();
            Duration duration = Duration.between(blockedInstant, currentInstant);

            System.out.println(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE));
            System.out.println(showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED));
            System.out.println(duration.toMinutes() > 15);

            if (!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                            duration.toMinutes() > 15
                    ))) {
                throw new RuntimeException();
            }
        }*/

        List<ShowSeat> savedShowSeats = new ArrayList<>();

        // 6. If yes, Mark the status of show seats as LOCKED
       for (ShowSeat showSeat : showSeats) {
           showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
           // 7.Save updated show seats to DB
          savedShowSeats.add(showSeatRepository.save(showSeat));
       }


        // ----------END LOCK----------
        // 8. create Booking object
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShowSeats(savedShowSeats);
        booking.setUser(bookedBy);
        booking.setBookedAt(new Date());
        booking.setShow(bookedShow);
        booking.setAmount(priceCalculator.calculatePrice(savedShowSeats, bookedShow));



        // 9. return booking object
        Booking savedBooking = bookingRepository.save(booking);

        return savedBooking;
    }
}
