package com.scaler.bookmyshow.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ShowInfoRepositoryImpl implements ShowInfoRepository{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Object[]> getShowDetails(long movieId) {


            String sql = "SELECT s.show_id, s.start_time, s.end_time, s.screen_name, s.theater_name, s.region_name, " +
                    "s.seat_id, s.row_val, s.col_val, s.number AS seat_number, st.name AS seat_name " +
                    "FROM bookmyshow.seat_type st " +
                    "JOIN ( " +
                    "    SELECT sa.show_id, sa.start_time, sa.end_time, sa.screen_name, sa.theater_name, " +
                    "           sa.region_name, s.id as seat_id, s.seat_type_id, s.row_val, s.col_val, s.number " +
                    "    FROM bookmyshow.seat s " +
                    "    JOIN ( " +
                    "        SELECT sc.id AS screen_id, cte.id AS show_id, cte.start_time, cte.end_time, " +
                    "               sc.name AS screen_name, t.name AS theater_name, r.name AS region_name " +
                    "        FROM bookmyshow.screen sc " +
                    "        JOIN ( " +
                    "            SELECT sh.id, sh.screen_id, sh.start_time, sh.end_time " +
                    "            FROM bookmyshow.shows sh " +
                    "            WHERE sh.movie_id = :movieId " +
                    "        ) cte ON sc.id = cte.screen_id " +
                    "        JOIN bookmyshow.theater t ON sc.theater_id = t.id " +
                    "        JOIN bookmyshow.region r ON t.region_id = r.id " +
                    "    ) sa ON s.screen_id = sa.screen_id " +
                    ") s ON s.seat_type_id = st.id";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("movieId", movieId);

            return query.getResultList();

    }
}
