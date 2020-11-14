package com.crash.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crash.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

}
