package com.scaler.bookmyshow.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.scaler.bookmyshow.dtos.ShowResponseListDto;

import java.util.List;

public interface ShowService {
    ShowResponseListDto getShows(long id);
}
