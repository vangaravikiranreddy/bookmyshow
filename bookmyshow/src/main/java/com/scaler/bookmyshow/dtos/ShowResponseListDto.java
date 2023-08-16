package com.scaler.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowResponseListDto {
    private String movieUrl;
    private String errorMsg;
    private List<ShowResponse> shows;

}
