package com.crash.tests.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crash.dto.MovieDTO;
import com.crash.entities.Movie;
import com.crash.repositories.MovieRepository;
import com.crash.services.MovieService;
import com.crash.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class MoviesServiceTests {

	@InjectMocks
	private MovieService service;
	
	@Mock
	private MovieRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private PageImpl<Movie> page;
	private PageRequest pageRequest;
	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 100L;
		pageRequest = PageRequest.of(0, 3);
		Movie movie = new Movie();
		page = new PageImpl<>(List.of(movie));
		Mockito.when(repository.find(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(movie));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
	}
	
	@Test
	public void findAllPagedShouldReturnPageMovie() {
		Page<MovieDTO> movie = service.findAllPaged("Aventura", pageRequest);
		Assertions.assertFalse(movie.isEmpty());
	}
	
	@Test
	public void findByIdShouldReturnIdMovieWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.findById(existingId);	
		});
		verify(repository, times(1)).findById(existingId);
	}
	
	@Test
	public void findByIdShouldResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class , () -> {
			service.findById(nonExistingId);	
		});
		verify(repository, times(1)).findById(nonExistingId);
	}
}