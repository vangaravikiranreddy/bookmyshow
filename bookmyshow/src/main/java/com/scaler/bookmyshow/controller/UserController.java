package com.scaler.bookmyshow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.bookmyshow.dtos.LoginRequestDto;
import com.scaler.bookmyshow.dtos.LoginResponseDto;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private  UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        logger.log(Level.INFO, "Start of login "+objectMapper.writeValueAsString(request));

        User user;
        LoginResponseDto response = new LoginResponseDto();

        try {
            String email = request.getEmail();
            String password = request.getPassword();
            user = userService.login(
                    email, password
            );
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setUserId(user.getId());

            logger.log(Level.INFO, "end of login "+objectMapper.writeValueAsString(response));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);

            logger.log(Level.INFO, "end of login "+e);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

