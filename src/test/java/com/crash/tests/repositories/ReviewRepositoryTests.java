package com.crash.tests.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crash.entities.Review;
import com.crash.repositories.ReviewRepository;
import com.crash.tests.factory.ReviewFactory;

@DataJpaTest
public class ReviewRepositoryTests {

	@Autowired
	private ReviewRepository repository;

	private long totalElementReview;

	@BeforeEach
	public void setUp() throws Exception {
		totalElementReview = 10L;
	}

	@Test
	public void saveShoulNewReviewWhenIdIsNull() {
		Review review = ReviewFactory.createReview();
		review.setId(null);
		review = repository.save(review);
		Optional<Review> optional = repository.findById(review.getId());
		Assertions.assertNotNull(review.getId());
		Assertions.assertEquals(totalElementReview + 1L, review.getId());
		Assertions.assertTrue(optional.isPresent());
		Assertions.assertSame(optional.get(), review);
	}
}
