package com.crash.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
