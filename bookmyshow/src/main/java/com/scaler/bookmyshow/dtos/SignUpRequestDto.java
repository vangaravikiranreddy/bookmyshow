package com.scaler.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
}
