package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.Show;
import com.scaler.bookmyshow.models.ShowSeat;

import java.util.List;

public interface PriceCalculator {
    int calculatePrice(List<ShowSeat> seats, Show show);
}
