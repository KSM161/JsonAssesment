package com.assignment.Signify.service;

import com.assignment.Signify.Builders.ReviewBuilder;
import com.assignment.Signify.model.Review;
import com.assignment.Signify.model.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FetchServiceTest {

    @MockBean
    ReviewRepository reviewRepository;

    @Autowired
    FetchService fetchService;
    List<Review> reviewList = ReviewBuilder.build();

    @Test
    void shouldFetchReviewsWhenNoFiltersApllied() {

        when(reviewRepository.findAllReviews()).thenReturn(reviewList);
        assertEquals(fetchService.fetchReviews(null,null,0).get().size(),reviewList.size());

    }

    @Test
    void shouldFetchReviewsWhenValidDateFilterisAplied() {
        String dateFilter = "2018-02-01";
        AtomicBoolean containsOnlyFileterdDate = new AtomicBoolean(true);

        when(reviewRepository.findAllReviews()).thenReturn(reviewList);
        List<Review> resultList =  fetchService.fetchReviews(dateFilter,null,0).get();
        resultList.forEach(x->{if(!x.getReviewedDate().contains(dateFilter)) containsOnlyFileterdDate.set(false);});
        assertTrue(containsOnlyFileterdDate.get());
    }

    @Test
    void shouldFetchReviewsWhenValidStoreTypeFilterisAplied() {
        String storeTypeFilter = "iTunes";
        AtomicBoolean containsOnlystoreTypeFilter = new AtomicBoolean(true);
        when(reviewRepository.findAllReviews()).thenReturn(reviewList);
        List<Review> resultList =  fetchService.fetchReviews(null,storeTypeFilter,0).get();
        resultList.forEach(x->{if(!x.getReviewSource().contains(storeTypeFilter)) containsOnlystoreTypeFilter.set(false);});
        assertTrue(containsOnlystoreTypeFilter.get());

    }

    @Test
    void shouldFetchReviewsWhenValiddateRatingFilterisAplied() {
        int ratingFilter = 1;


        when(reviewRepository.findAllReviews()).thenReturn(reviewList);
        List<Review> resultList =  fetchService.fetchReviews(null,null,ratingFilter).get();
        boolean containsOnlyFilteredRating = resultList.stream().filter(x -> x.getRating() != ratingFilter).toList().isEmpty();
        assertTrue(containsOnlyFilteredRating);
    }
}