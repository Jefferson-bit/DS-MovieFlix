package com.crash.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crash.dto.GenreDTO;
import com.crash.entities.Genre;
import com.crash.repositories.GenreRepository;

@Service
public class GenreService {
	
	@Autowired
	private GenreRepository repository;
	
	@Transactional(readOnly = true)
	public Page<GenreDTO> findAllPaged(PageRequest pageRequest, String name){
		Page<Genre> page = repository.searchForGenre(name, pageRequest);
		repository.findGenresMovie(page.stream().collect(Collectors.toList()));
		return page.map(obj -> new GenreDTO(obj, obj.getMovies()));
	}	
	
	public List<Genre> findAll(){
		return repository.findAll();
	}
}
