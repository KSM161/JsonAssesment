package com.assignment.Signify.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends ListCrudRepository<ReviewEntity,Long> {

     default List<Review> findAllReviews(){
        return findAll().stream().map(ReviewEntity::toReview).toList();
    }



}
