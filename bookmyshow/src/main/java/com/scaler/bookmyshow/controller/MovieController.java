package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dtos.MovieResponseDto;
import com.scaler.bookmyshow.models.Movie;
import com.scaler.bookmyshow.services.MovieServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class MovieController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    private MovieServie movieServie;

    @Autowired
    public MovieController(MovieServie movieServie) {
        this.movieServie = movieServie;
    }

    @GetMapping("/movies")
    @ResponseBody
    public ResponseEntity<MovieResponseDto> movies() {
        logger.log(Level.INFO, "Start of movies : " );
        MovieResponseDto response = new MovieResponseDto();

        List<Movie> movies = movieServie.getMovies();

        response.setMovies(movies);

        logger.log(Level.INFO, "End of movies : ");
        return ResponseEntity.ok(response);
    }

}
