package com.assignment.Signify.controller;

import com.assignment.Signify.Builders.ReviewBuilder;
import com.assignment.Signify.model.Review;
import com.assignment.Signify.service.FetchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(FetchController.class)
class FetchControllerTest {

    @MockBean
    FetchService fetchService;

    @Autowired
    MockMvc mockMvc;


    @Test
    void shouldRetrunHttpStatusOkWhenNoFiltersAreApplied() throws Exception {
        when(fetchService.fetchReviews(null,null,0)).thenReturn(Optional.of(ReviewBuilder.build()));
        mockMvc.perform(get("/fetch").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());

    }

    @Test
    void shouldReturnNotFoundIfFetchReviewsReturnsNull() throws Exception {
        when(fetchService.fetchReviews(null,null,0)).thenReturn(Optional.ofNullable(null));
        mockMvc.perform(get("/fetch").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }
    @Test
    void shouldReturnHTTPStatusOkIfValidDateFilterProvided() throws Exception {
        String date = "2018-02-02";
        ArrayList<Review> result  = new ArrayList<>();
        when(fetchService.fetchReviews(date,null,0)).thenReturn(Optional.of(result));

        mockMvc.perform(get("/fetch?date={date}",date).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());

    }

    @Test
    void shouldReturnAPIExceptionWhenDateFilterFormatIsInvalid() throws Exception {
        String exception = Arrays.asList("invalid format for parameter date : "+"2018-02-01T03:43:09.000Z").toString();
        String date = "2018-02-01T03:43:09.000Z";

        mockMvc.perform(get("/fetch?date={date}",date)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).
                andExpect(status().isBadRequest())
        .andExpect(result ->
                    assertEquals(exception,result.getResolvedException().getMessage()));
    }
    @Test
    void shouldReturnHTTPStatusOkWhenValidRatingFilterIsProvided() throws Exception {
        int rating = 3;
        ArrayList<Review> result  = new ArrayList<>();
        when(fetchService.fetchReviews(null,null,rating)).thenReturn(Optional.of(result));

        mockMvc.perform(get("/fetch?rating={rating}",rating).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());

    }

    @Test
    void shouldReturnAPIExceptioWhenRatingFilterIsOutOfRange() throws Exception {
        String exception = List.of("value is out of range for paramter rating , Accepted Range is 0-5").toString();
        mockMvc.perform(get("/fetch?rating={rating}",-1)
                        .contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest()).andExpect(result ->
                        assertEquals(exception,result.getResolvedException().getMessage()));

    }
    @Test
    void shouldReturnHTTPStatusOkWhenValidStoreTypeFilterIsProvided() throws Exception {
        String storeType = "iTunes";
        ArrayList<Review> result  = new ArrayList<>();
        when(fetchService.fetchReviews(null,storeType,0)).thenReturn(Optional.of(result));

        mockMvc.perform(get("/fetch?store-type={storeType}",storeType).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());

    }

    @Test
    void shouldReturnAPIExceptionWhenStoreTypeFilterIsInvalid() throws Exception {
        String storeType = "Bad store type";
        String exception = List.of("invalid value for paramter store-type : "+ storeType).toString();
        mockMvc.perform(get("/fetch?store-type={store-type}",storeType)
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print()).
                andExpect(status().isBadRequest()).andExpect(result ->
                        assertEquals(exception,result.getResolvedException().getMessage()));

    }
}