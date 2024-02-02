package com.assignment.Signify.service;

import com.assignment.Signify.model.Review;
import com.assignment.Signify.model.ReviewEntity;
import com.assignment.Signify.model.ReviewRepository;
import com.assignment.Signify.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FetchService {

    private ReviewRepository reviewRepository;

    public Optional<List<Review>> fetchReviews(String date, String storeType, int rating) {
        List<Review> result = reviewRepository.findAllReviews();

        if(rating > 0)
            result = result.stream().filter(x-> x.getRating()==rating).toList();


        if(storeType != null)
            result = result.stream().filter(x->  x.getReviewSource().equals(storeType)).collect(Collectors.toList());


        if(null != date)
            result =  result.stream().filter(x-> DateUtil.dateFilter(date,x.getReviewedDate())).collect(Collectors.toList());

        return Optional.ofNullable(result);
    }

}
