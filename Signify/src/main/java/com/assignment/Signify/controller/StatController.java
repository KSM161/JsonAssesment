package com.assignment.Signify.controller;

import com.assignment.Signify.exception.ApiRequestException;
import com.assignment.Signify.service.StatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/stat")
@AllArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/ratings-average")
    public ResponseEntity<Map<String,Double>> monthlyAverage(@RequestParam(value = "year", defaultValue = "0",required = true) int year,
                                                             @RequestParam(value = "month", defaultValue ="1000", required = true) int month){

        List<String> validationExceptions = validateParamters(month,year);
        System.out.println(validationExceptions);
        if(!validationExceptions.isEmpty()) throw new ApiRequestException(validationExceptions.toString());

        Optional<Map<String, Double>> result  = statService.getMonthlyAverage(year,month);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/ratings-count")
    public ResponseEntity<Map<Integer, Long>> ratingsCount(){
        Optional<Map<Integer, Long>> result =  statService.getTotalRatingsInEachCatagory();

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    public List<String> validateParamters(int month, int year){
        List<String> validationsViolations =  new ArrayList<>();
        if(month<1 || month > 12)
            validationsViolations.add("value is out of range for paramter month. Accepted Range is 1-12");
        if(year<1000) validationsViolations.add("value for the Year is Mandatory ");
        return validationsViolations;
    }
}
