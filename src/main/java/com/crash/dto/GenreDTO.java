package com.crash.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.crash.entities.Genre;
import com.crash.entities.Movie;

public class GenreDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	private List<MovieDTO> movies = new ArrayList<>();

	public GenreDTO() {
	}
	
	public GenreDTO(Genre entity, List<Movie> movie)  {
		this(entity);
		movie.forEach(mov -> this.movies.add(new MovieDTO(mov)));
	}
	
	public GenreDTO(Genre entity) {
		id = entity.getId();
		name = entity.getName();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MovieDTO> getMovies() {
		return movies;
	}
}
