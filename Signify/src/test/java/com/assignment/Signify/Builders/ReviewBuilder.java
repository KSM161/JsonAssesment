package com.assignment.Signify.Builders;

import com.assignment.Signify.model.Review;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReviewBuilder {

    public static List<Review> build(){
        List<Review> reviewList =  new ArrayList<>();
        String review = "Cannot get past “Give Amazon permissions";
        String date = "2018-02-01T03:43:09.000Z";
        String source1 = "iTunes"; String source2 = "GooglePlayStore";
        String author = "author";
        String title = "title";
        String productName = "Amazon Alexa";

        reviewList.addAll(getReviews(review,author,source1,title,productName,date,18));
        reviewList.addAll(getReviews(review,author,source2,title,productName,date,23));



        return reviewList;

    }

    private static Collection<? extends Review> getReviews(String review, String author,
                                                           String source, String title, String productName,
                                                           String date, int count) {
        List<Review> reviewList =  new ArrayList<>();
        for(int index=0 ;  index < count ; index++){
            reviewList.add(new Review(review,author,source,index%5+1,title,productName,date));
        }
        return reviewList;
    }

}
