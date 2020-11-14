package com.crash.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crash.dto.MovieDTO;
import com.crash.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MoviesResource {
	
	@Autowired
	private MovieService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDTO> findById(@PathVariable Long id){
		MovieDTO movie = service.findById(id);
		return ResponseEntity.ok().body(movie);
	}
	
	@GetMapping
	public ResponseEntity<Page<MovieDTO>> findAllPage(
			@RequestParam(value = "page", defaultValue = "0")Integer page,
			@RequestParam(value = "size", defaultValue = "10")Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC")String direction,
			@RequestParam(value = "orderBy", defaultValue = "title")String orderBy){
	PageRequest pageable = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
	Page<MovieDTO> list = service.findAllPaged(pageable);
	return ResponseEntity.ok().body(list);
	}
}
