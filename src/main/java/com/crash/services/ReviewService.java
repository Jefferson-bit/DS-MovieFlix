package com.crash.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crash.dto.ReviewDTO;
import com.crash.entities.Review;
import com.crash.repositories.MovieRepository;
import com.crash.repositories.ReviewRepository;
import com.crash.repositories.UserRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private MovieRepository movieRepository;
	 
	public ReviewDTO saveReview(ReviewDTO dto) {
		Review entity = new Review();
		entity.setMovie(movieRepository.getOne(dto.getMovieId()));
		entity.setUser(userRepository.getOne(authService.userLogger().getId()));
		entity.setText(dto.getText());
		repository.save(entity);	
		return new ReviewDTO(entity);
	}
}






