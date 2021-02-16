package com.crash.tests.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.crash.entities.Movie;
import com.crash.repositories.MovieRepository;

@DataJpaTest
public class MovieRepositoryTests {
	
	@Autowired
	private MovieRepository repository;
	
	private long totalElement;
	private long countTotalProduct;
	private PageRequest pageRequest;

	
	@BeforeEach
	void setUp() throws Exception {
		totalElement = 3;
		pageRequest = PageRequest.of(0, 1);
		countTotalProduct = 10;
	}
	
	@Test
	public void findShouldReturnAllFilmeWhenNameIsNull() {
		String name = "";
		Page<Movie> result = repository.find(name, pageRequest);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countTotalProduct, result.getTotalElements());
	}
	
	@Test
	public void findShouldReturnFilmeForGenreWhenNameExists() {
		String name = "Aventura";
		Page<Movie> result = repository.find(name, pageRequest);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(totalElement, result.getTotalElements());
	}
}
