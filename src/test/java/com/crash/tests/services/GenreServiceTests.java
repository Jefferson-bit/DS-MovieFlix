package com.crash.tests.services;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.crash.dto.GenreDTO;
import com.crash.entities.Genre;
import com.crash.repositories.GenreRepository;
import com.crash.services.GenreService;

@ExtendWith(SpringExtension.class)
public class GenreServiceTests {

	@InjectMocks
	private GenreService service;

	@Mock
	private GenreRepository repository;

	private List<Genre> genreList;
	private PageImpl<Genre> page;
	private PageRequest pageRequest;

	@BeforeEach
	public void setUp() throws Exception {
		pageRequest = PageRequest.of(1, 3);
		Genre genre = new Genre();
		genreList = new ArrayList<>();
		page = new PageImpl<>(List.of(genre));
		Mockito.when(repository.searchForGenre(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.findGenresMovie(ArgumentMatchers.any())).thenReturn(genreList);
	}

	@Test
	public void findGenresMovieShouldGenreForFilmeWhenListGenreExisting() {
		String genre = "Aventura";
		Page<GenreDTO> pageDTO = service.findAllPaged(pageRequest, genre);
		Assertions.assertFalse(pageDTO.isEmpty());
		Mockito.verify(repository, times(1)).findGenresMovie(page.stream().collect(Collectors.toList()));
	}
}
