package com.crash.tests.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.crash.dto.MovieDTO;
import com.crash.services.MovieService;
import com.crash.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieResourceTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MovieService service;
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	private long existingId;
	private long nonExistingId;
	private MovieDTO movieDTO;
	private PageImpl<MovieDTO> page;
	
	@BeforeEach
	public void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 2L;
		movieDTO =  new MovieDTO();
		page = new PageImpl<>(List.of(movieDTO));
		when(service.findById(existingId)).thenReturn(movieDTO);
		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		when(service.findAllPaged(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(page);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() throws Exception {
		ResultActions result = mockMvc.perform(get("/movies")
				.accept(MediaType.APPLICATION_JSON));
				result.andExpect(status().isOk());
				result.andExpect(jsonPath("$.content").exists());
	}
	
	
	@Test
	public void findByIdShouldReturnMovieWhenIdEXists() throws Exception {
		ResultActions result = mockMvc.perform(get("/movies/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON));
				result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldNotFoundWhenDoesNotExists() throws Exception {
		ResultActions result = mockMvc.perform(get("/movies/{id}", nonExistingId)
				.accept(MediaType.APPLICATION_JSON));
				result.andExpect(status().isNotFound());
	}
	
	
	
	
	
}
