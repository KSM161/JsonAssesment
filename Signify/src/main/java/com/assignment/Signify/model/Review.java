package com.assignment.Signify.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {


    private String review;
    private String author;
    @JsonProperty("review_source")
    private String reviewSource;

    private int rating;
    private String title;
    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("reviewed_date")
    private String reviewedDate;


}
