package com.assignment.Signify.service;

import com.assignment.Signify.model.Review;
import com.assignment.Signify.model.ReviewEntity;
import com.assignment.Signify.model.ReviewRepository;
import com.assignment.Signify.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatService {

    private ReviewRepository reviewRepository;

    public Optional<Map<Integer, Long>> getTotalRatingsInEachCatagory() {
         List<Review> reviews = reviewRepository.findAllReviews();
         Map<Integer,Long> result = countRatingsByCategory(reviews);
         for(int rating = 1 ;rating <=5 ; rating++ ){
             if(!result.containsKey(rating)) result.put(rating, 0L);
         }
        return  Optional.ofNullable(result);


    }

    public static Map<Integer, Long> countRatingsByCategory(List<Review> reviews) {
        return reviews.stream()
                .collect(Collectors.groupingBy(Review::getRating, Collectors.counting()));

    }
    public Optional<Map<String, Double>> getMonthlyAverage(int year, int month) {

        List<Review> reviews = reviewRepository.findAllReviews();

        Map<String, Double> averageRatingsByStore = reviews.stream()
                .filter(x-> DateUtil.monthOfYearFilter(x.getReviewedDate(),year,month))
                .collect(Collectors.groupingBy(
                        Review::getReviewSource,
                        Collectors.averagingInt(Review::getRating)
                ));
        if(!averageRatingsByStore.containsKey("iTunes")) averageRatingsByStore.put("iTunes",0.00);
        if(!averageRatingsByStore.containsKey("GooglePlayStore")) averageRatingsByStore.put("GooglePlayStore",0.00);
        return Optional.of(averageRatingsByStore);
    }
}
