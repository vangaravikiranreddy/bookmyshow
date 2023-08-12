package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.User;

public interface UserService {
    User login(String email, String password);
    User signUp(String email, String password);
}
