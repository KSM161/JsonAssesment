package com.assignment.Signify.controller;

import com.assignment.Signify.exception.ApiRequestException;
import com.assignment.Signify.model.Review;
import com.assignment.Signify.service.FetchService;
import com.assignment.Signify.service.StatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/fetch")
@AllArgsConstructor
public class FetchController {

    private final FetchService fetchService;


    @GetMapping("")
    public ResponseEntity<List<Review>> fetchReviews(@RequestParam(value = "date" , required = false) String date,
                                                     @RequestParam(value = "store-type",required = false) String storeType,
                                                     @RequestParam(value = "rating",defaultValue = "0",required = false) int rating){

        List<String> validationResult = validateFetchParameters(date, storeType, rating);

        if(!validationResult.isEmpty())
            throw new ApiRequestException(validationResult.toString());

        Optional<List<Review>> result =  fetchService.fetchReviews(date,storeType,rating);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    private List<String> validateFetchParameters(String date,String storeType,int rating) {
        List<String> errors =  new ArrayList<>();
        if(rating < 0 || rating > 5) errors.add("value is out of range for paramter rating , Accepted Range is 0-5");
        if(null!= storeType && !Arrays.asList("iTunes","GooglePlayStore").contains(storeType)) errors.add("invalid value for paramter store-type : "+ storeType);
        if(null!=date && date.indexOf('T')>-1)errors.add("invalid format for parameter date : "+date);

        return errors;
    }





}
