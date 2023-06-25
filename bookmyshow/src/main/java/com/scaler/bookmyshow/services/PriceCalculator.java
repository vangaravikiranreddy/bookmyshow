package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.Show;
import com.scaler.bookmyshow.models.ShowSeat;
import com.scaler.bookmyshow.models.ShowSeatType;
import com.scaler.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculator {

    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculatePrice(List<ShowSeat> seats, Show show) {
        // 1. Get ShowSeatType for that show
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);

        int amount = 0;

        //2.Get SeatType for all seats
        for (ShowSeat showSeat : seats) {
            for (ShowSeatType showSeatType : showSeatTypes) {
                if (showSeat.getSeat().equals(showSeatType.getSeatType())) {
                    amount += showSeatType.getPrice();
                    break;
                }
            }
        }
        //3. Add amount for all seats

        return amount;
    }
}
