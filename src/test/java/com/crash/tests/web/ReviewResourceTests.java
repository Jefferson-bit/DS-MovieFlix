package com.crash.tests.web;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.crash.dto.ReviewDTO;
import com.crash.services.ReviewService;
import com.crash.services.exceptions.ForbiddenException;
import com.crash.services.exceptions.UnauthorizedException;
import com.crash.tests.factory.ReviewFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewResourceTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ReviewService service;
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	private ReviewDTO reviewDTO;
	private String operatorName;
	private String operatorPassword;
	private long movieId;
	
	@BeforeEach
	void setUp() throws Exception{
		operatorName = "alex@gmail.com";
		operatorPassword = "123456";
		movieId = 1L;
		reviewDTO = ReviewFactory.createReviewDTO(movieId);
		when(service.saveReview(ArgumentMatchers.any())).thenReturn(reviewDTO);
		doThrow(UnauthorizedException.class).when(service).saveReview(reviewDTO);
		doThrow(ForbiddenException.class).when(service).saveReview(reviewDTO);
;	}
	
	@Test
	public void saveReviewShouldReturnForbiddenExceptionWhenRoleDoesNotMember() throws Exception{
		String jsonBody = objectMapper.writeValueAsString(reviewDTO);
		String accessToken = obtainAccessToken("maria@gmail.com", "123456");

		this.mockMvc.perform(post("/reviews")
				.header("Authorization", "Bearer " + accessToken)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void saveReviewShouldReturnUnauthorizedExceptionWhenTokenIsEmpty() throws Exception{
		String jsonBody = objectMapper.writeValueAsString(reviewDTO);
		
		this.mockMvc.perform(post("/reviews")
				.header("Authorization", "Bearer " + null)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void saveReviewShouldPersistWithAutoIncrementWhenUserIsMember() throws Exception{
		String accessToken = obtainAccessToken(operatorName, operatorPassword);
		String jsonBody = objectMapper.writeValueAsString(reviewDTO);
		
		this.mockMvc.perform(post("/reviews")
				.header("Authorization", "Bearer " + accessToken)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		 
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("client_id", clientId);
	    params.add("username", username);
	    params.add("password", password);
	 
	    ResultActions result 
	    	= mockMvc.perform(post("/oauth/token")
	    		.params(params)
	    		.with(httpBasic(clientId, clientSecret))
	    		.accept("application/json;charset=UTF-8"))
	        	.andExpect(status().isOk())
	        	.andExpect(content().contentType("application/json;charset=UTF-8"));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}	
}
