package com.assignment.Signify.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "reviews")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review", columnDefinition="CLOB NOT NULL")
    @Lob
    private String review;
    private String author;
    private String reviewSource;
    private int rating;
    private String title;
    private String productName;

    private String reviewedDate;

    public ReviewEntity(Review review) {
        this.review = review.getReview();
        this.author = review.getAuthor();
        this.reviewSource = review.getReviewSource();
        this.rating = review.getRating();
        this.title = review.getTitle();
        this.productName = review.getProductName();
        this.reviewedDate = review.getReviewedDate();

    }


    public Review toReview(){
        Review review =  new Review();
        review.setReview(this.getReview());
        review.setReviewSource(this.getReviewSource());
        review.setRating(this.getRating());
        review.setAuthor(this.getAuthor());
        review.setTitle(this.getTitle());
        review.setProductName(this.getProductName());
        review.setReviewedDate(this.reviewedDate);
        return review;

    }


}
