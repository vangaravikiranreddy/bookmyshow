package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.dtos.SignUpRequestDto;
import com.scaler.bookmyshow.dtos.SignUpResponseDto;
import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto request) {
        User user;
        SignUpResponseDto response = new SignUpResponseDto();

        try {
            user = userService.signUp(
                    request.getEmail(), request.getPassword()
            );
             response.setResponseStatus(ResponseStatus.SUCCESS);
             response.setUserId(user.getId());
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }

        return response;
    }
}
