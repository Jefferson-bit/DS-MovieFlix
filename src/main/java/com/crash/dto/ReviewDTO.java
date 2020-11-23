package com.crash.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.crash.entities.Review;

public class ReviewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank(message = "Campo não pode ser vazio")
	private String text;
	private Long movieId;
	
	public ReviewDTO() {
	}

	public ReviewDTO(Long id, String text, Long movieId) {
		this.id = id;
		this.text = text;
		this.movieId = movieId;
	}

	public ReviewDTO(Review entity) {
		id = entity.getId();
		text = entity.getText();
		movieId = entity.getMovie().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
}
