package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dtos.ShowResponseListDto;
import com.scaler.bookmyshow.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ShowController {
    private static final Logger logger = Logger.getLogger(ShowController.class.getName());
    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping("/shows")
    @ResponseBody
    public ResponseEntity<ShowResponseListDto> shows(@RequestParam Long id)  {
        logger.log(Level.INFO, "Start of show :ShowController " );

        ShowResponseListDto response = showService.getShows(id);

        logger.log(Level.INFO, "end of show :ShowController " );
        return ResponseEntity.ok(response);
    }
}
