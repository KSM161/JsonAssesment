package com.assignment.Signify.service;

import com.assignment.Signify.model.Review;
import com.assignment.Signify.model.ReviewEntity;
import com.assignment.Signify.model.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@SpringBootTest
class WriteServiceTest {

    @MockBean
    ReviewRepository reviewRepository;

    @Autowired
    WriteService writeService;

    Review testReview = new Review("Cannot get past “Give Amazon permissions",
            "author","iTunes",1,
            "Bad update","Amazon Alexa",
            "2024-02-29T03:43:09.000Z");

    @Test
    void shouldInvokeSaveMethodOfReviewRepositoryOnce() {

        writeService.storeReview(testReview);
        verify(reviewRepository,times(1)).save(new ReviewEntity(testReview  ));
    }
}