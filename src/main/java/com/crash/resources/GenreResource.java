package com.crash.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crash.dto.GenreDTO;
import com.crash.services.GenreService;

@RestController
@RequestMapping(value = "/genres")
public class GenreResource {
	
	@Autowired
	private GenreService service;
	
	@GetMapping
	public ResponseEntity<Page<GenreDTO>> findAllPage(
			@RequestParam(value = "nameByGenre", defaultValue = "") String nameByGenre,
			@RequestParam(value = "page", defaultValue = "0")Integer page,
			@RequestParam(value = "size", defaultValue = "3")Integer size){
	PageRequest pageable = PageRequest.of(page, size);
	Page<GenreDTO> list = service.findAllPaged(pageable, nameByGenre);
	return ResponseEntity.ok().body(list);
	}
	
}
