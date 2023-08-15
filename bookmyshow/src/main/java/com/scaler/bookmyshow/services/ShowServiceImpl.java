package com.scaler.bookmyshow.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.scaler.bookmyshow.dtos.SeatResponse;
import com.scaler.bookmyshow.dtos.ShowResponse;
import com.scaler.bookmyshow.dtos.ShowResponseListDto;
import com.scaler.bookmyshow.repositories.ShowInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ShowServiceImpl implements ShowService{
    private static final Logger logger = Logger.getLogger(ShowServiceImpl.class.getName());
    private final ShowInfoRepository showInfoRepository;

    @Autowired
    public ShowServiceImpl(ShowInfoRepository showInfoRepository) {
        this.showInfoRepository = showInfoRepository;
    }

    @Override
    public  ShowResponseListDto getShows(long id) {
        logger.log(Level.INFO, "Start of getShows : ShowServiceImpl"+id );

        ShowResponseListDto response = new ShowResponseListDto();

        List<ShowResponse> showResponses = new ArrayList<>();

        List<Object[]> objects = showInfoRepository.getShowDetails(id);

        HashSet<Long> hashSet = new HashSet<>();

      try {
          for (Object[] r : objects) {

              if (!hashSet.contains((long)r[0])) {
                  hashSet.add((long)r[0]);
                  // Setting show values
                  ShowResponse showResponse = new ShowResponse();
                  showResponse.setShowId((long) r[0]);
                  showResponse.setStartTime((Date) r[1]);
                  showResponse.setEndTime((Date) r[2]);
                  showResponse.setScreenName((String) r[3]);
                  showResponse.setTheaterName((String) r[4]);
                  showResponse.setRegionName((String) r[5]);
                  List<SeatResponse> seatResponses = new ArrayList<>();
                  // Setting Seat values
                  SeatResponse seatResponse = new SeatResponse();
                  seatResponse.setRowVal((int) r[6]);
                  seatResponse.setColVal((int) r[7]);
                  seatResponse.setSeatNumber((String) r[8]);
                  seatResponse.setSeatName((String) r[9]);
                  seatResponses.add(seatResponse);
                  showResponse.setSeats(seatResponses);
                  showResponses.add(showResponse);
              } else {
                  // fetch the recent ShowResponse from list
                  int lastIndex = showResponses.size() - 1;
                  ShowResponse showResponse = showResponses.get(lastIndex);
                  List<SeatResponse> seatResponses = showResponse.getSeats();
                  SeatResponse seatResponse = new SeatResponse();
                  seatResponse.setRowVal((int) r[6]);
                  seatResponse.setColVal((int) r[7]);
                  seatResponse.setSeatNumber((String) r[8]);
                  seatResponse.setSeatName((String) r[9]);
                  seatResponses.add(seatResponse);
              }
          }
          response.setShows(showResponses);
      } catch (Exception e) {
          response.setErrorMsg(e.getMessage());
      }


        Gson gson = new Gson();
        String jsonString = gson.toJson(response);

      logger.log(Level.INFO, "End of getShows : ShowServiceImpl \n"+ jsonString);

      return response;
    }
}
