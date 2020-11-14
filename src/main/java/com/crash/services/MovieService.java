package com.crash.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crash.dto.MovieDTO;
import com.crash.entities.Movie;
import com.crash.repositories.MovieRepository;
import com.crash.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Transactional(readOnly = true)
	public MovieDTO findById (Long id){
		Optional<Movie> movie = repository.findById(id);
		Movie entity = movie.orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id)); 
		return new MovieDTO(entity, entity.getReviews());
	}
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findAllPaged(PageRequest pageRequest){
		Page<Movie> page = repository.findAll(pageRequest);
		return page.map(x -> new MovieDTO(x));
	}
}
