package com.assignment.Signify.service;

import com.assignment.Signify.model.Review;
import com.assignment.Signify.model.ReviewEntity;
import com.assignment.Signify.model.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WriteService {
    private final ReviewRepository reviewRepository;

    public void storeReview(Review review) {
        ReviewEntity entity = new ReviewEntity(review);
        reviewRepository.save(entity);


    }
}
