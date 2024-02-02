package com.assignment.Signify.service;

import com.assignment.Signify.Builders.ReviewBuilder;
import com.assignment.Signify.model.Review;
import com.assignment.Signify.model.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class StatServiceTest {

    @MockBean
    ReviewRepository reviewRepository;

    @Autowired
    StatService statService;
    List<Review> reviewList = ReviewBuilder.build();
    @Test
    void shouldCalculateTotalRatingsOfEachCatagory() {
        // rating count 1* = 9 , 2* = 9 ,3* = 9 , 4* =7 , 5* =  7,
        when(reviewRepository.findAllReviews()).thenReturn(reviewList);
        Map<Integer,Long> result = statService.getTotalRatingsInEachCatagory().get();
        List<Integer> counts = Arrays.asList(9,9,9,7,7);

        for(int index = 0 ; index < 5 ; index++){
            assertEquals(Long.valueOf(counts.get(index)),result.get(index+1));
        }

    }

    @Test
    void shouldCalculateTotalRatingsAsIfCatagoryNotPresent() {
        // rating count 1* = 9 , 2* = 9 ,3* = 9 , 4* =7 , 5* =  7,
        reviewList =  reviewList.stream().filter(x->x.getRating()>3).toList();
        when(reviewRepository.findAllReviews()).thenReturn(reviewList);
        Map<Integer,Long> result = statService.getTotalRatingsInEachCatagory().get();
        List<Integer> counts = Arrays.asList(0,0,0,7,7);

        for(int index = 0 ; index < 5 ; index++){
            assertEquals(Long.valueOf(counts.get(index)),result.get(index+1));
        }

    }

    @Test
    void shouldGetMonthlyAverageFortheGivenMonthOfTheYear() {
        when(reviewRepository.findAllReviews()).thenReturn(reviewList);
        Map<String,Double> result = statService.getMonthlyAverage(2018,02).get();

        Double iTunesAverage = 2.8333333333333335;
        Double GooglePlayStoreAverage = 2.869565217391304;

        assertEquals(result.get("iTunes"),iTunesAverage);
        assertEquals(result.get("GooglePlayStore"),GooglePlayStoreAverage);
    }

   @Test
   void shouldContainStoreNameEvenIfNoNamesArePresent() {
        reviewList = reviewList.stream().filter(x->x.getReviewSource().equals("iTunes")).collect(Collectors.toList());
        when(reviewRepository.findAllReviews()).thenReturn(reviewList);
        Map<String,Double> result = statService.getMonthlyAverage(2018,02).get();

        Double iTunesAverage = 2.8333333333333335;


        assertEquals(result.get("iTunes"),iTunesAverage);
        assertEquals(result.get("GooglePlayStore"),0);
    }
}