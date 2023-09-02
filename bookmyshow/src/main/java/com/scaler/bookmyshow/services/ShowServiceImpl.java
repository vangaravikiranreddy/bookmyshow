package com.scaler.bookmyshow.services;

import com.google.gson.Gson;
import com.scaler.bookmyshow.dtos.SeatResponse;
import com.scaler.bookmyshow.dtos.ShowResponse;
import com.scaler.bookmyshow.dtos.ShowResponseListDto;
import com.scaler.bookmyshow.models.Movie;
import com.scaler.bookmyshow.repositories.MovieRepository;
import com.scaler.bookmyshow.repositories.ShowInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ShowServiceImpl implements ShowService{
    private static final Logger logger = Logger.getLogger(ShowServiceImpl.class.getName());
    private final ShowInfoRepository showInfoRepository;

    private final MovieRepository movieRepository;

    private String movieUrl;

    @Autowired
    public ShowServiceImpl(ShowInfoRepository showInfoRepository, MovieRepository movieRepository) {
        this.showInfoRepository = showInfoRepository;
        this.movieRepository = movieRepository;
    }

    public String getMovieUrl(Long id) throws Exception {
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isEmpty()) {
            throw new Exception();
        }
        Movie movie1 = movie.get();

        return movie1.getImageUrl();
    }

    @Override
    public  ShowResponseListDto getShows(long id) {
        logger.log(Level.INFO, "Start of getShows : ShowServiceImpl"+id );

        try {
            movieUrl = getMovieUrl(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ShowResponseListDto response = new ShowResponseListDto();

        List<ShowResponse> showResponses = new ArrayList<>();

        // fetch the list of shows using movie_id
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
                  seatResponse.setSeatId((long) r[6]);
                  seatResponse.setRowVal((int) r[7]);
                  seatResponse.setColVal((int) r[8]);
                  seatResponse.setSeatNumber((String) r[9]);
                  seatResponse.setSeatName((String) r[10]);
                  seatResponses.add(seatResponse);
                  showResponse.setSeats(seatResponses);
                  showResponses.add(showResponse);
              } else {
                  // fetch the recent ShowResponse from list
                  int lastIndex = showResponses.size() - 1;
                  ShowResponse showResponse = showResponses.get(lastIndex);
                  List<SeatResponse> seatResponses = showResponse.getSeats();
                  SeatResponse seatResponse = new SeatResponse();
                  seatResponse.setSeatId((long) r[6]);
                  seatResponse.setRowVal((int) r[7]);
                  seatResponse.setColVal((int) r[8]);
                  seatResponse.setSeatNumber((String) r[9]);
                  seatResponse.setSeatName((String) r[10]);
                  seatResponses.add(seatResponse);
              }
          }
          response.setMovieUrl(movieUrl);
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
