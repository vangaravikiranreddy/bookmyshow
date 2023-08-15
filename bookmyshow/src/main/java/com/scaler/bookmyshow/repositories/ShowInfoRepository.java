package com.scaler.bookmyshow.repositories;

import java.util.List;

public interface ShowInfoRepository {
    List<Object[]> getShowDetails(long movieId);
}
