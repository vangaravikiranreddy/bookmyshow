package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAll();

    @Override
    Optional<Movie> findById(Long aLong);
}