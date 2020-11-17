package com.crash.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.crash.entities.Review;

public class ReviewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank(message = "Campo não pode ser vazio")
	private String text;
	private Long userId;

	public ReviewDTO() {
	}

	public ReviewDTO(Long id, String text, Long userId) {
		this.id = id;
		this.text = text;
		this.userId = userId;
	}

	public ReviewDTO(Review entity) {
		id = entity.getId();
		text = entity.getText();
		userId = entity.getUser().getId();
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
