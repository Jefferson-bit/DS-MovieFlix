package com.crash.tests.factory;

import com.crash.dto.ReviewDTO;
import com.crash.entities.Genre;
import com.crash.entities.Movie;
import com.crash.entities.Review;
import com.crash.entities.User;

public class ReviewFactory {
	
	public static Review createReview() {
		User user = new User(1L, "Alex Brown", "alex@gmail.com", "123456");
		Genre genre = new Genre(1L, "Ação");
		Movie movie = new Movie
		(1L, "em busca", "por algo", 1999, "", "Testando alguns valores mockado", genre);
		return new Review(1L, "Um excelente filme", user, movie);
	}
	
	public static ReviewDTO createReviewDTO() {
		Review review = createReview();
		return new ReviewDTO(review);
	}
	
	public static ReviewDTO createReviewDTO(Long id) {
		ReviewDTO dto = createReviewDTO();
		dto.setMovieId(id);
		return dto;
	}
	
}
