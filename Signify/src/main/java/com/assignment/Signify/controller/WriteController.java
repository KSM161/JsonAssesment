package com.assignment.Signify.controller;

import com.assignment.Signify.model.Review;
import com.assignment.Signify.service.WriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;

@RestController
@RequestMapping("/write")
@AllArgsConstructor
public class WriteController {
    private final WriteService writeService;

    @PostMapping("/store-review")
    public ResponseEntity storeReadings(@RequestBody Review review){

        if(!isValidReview(review))
            return ResponseEntity.internalServerError().build();

        writeService.storeReview(review);
        return ResponseEntity.ok().build();
    }

    private boolean isValidReview(Review review) {
        return null != review.getReviewedDate() &&
                null !=review.getReviewSource() &&
                 review.getRating() > 0;
    }


}
