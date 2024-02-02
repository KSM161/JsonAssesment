package com.assignment.Signify.controller;

import com.assignment.Signify.model.Review;
import com.assignment.Signify.service.WriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WriteController.class)
class WriteControllerTest {

    @MockBean
    WriteService writeService;
    @Autowired
    MockMvc mockMvc;

    Review testReview = new Review("Cannot get past “Give Amazon permissions",
            "author","iTunes",1,
            "Bad update","Amazon Alexa",
            "2024-02-29T03:43:09.000Z");
    @Test
    void shouldStoreValidReviews() throws Exception {

        doNothing().when(writeService).storeReview(testReview);
        mockMvc.perform(post("/write/store-review").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testReview))).andExpect(status().isOk());
    }

    @Test
    void shouldReturnInternalServerErrorWhileStoringInvalidReviews() throws Exception{
        testReview.setRating(-1);
        doNothing().when(writeService).storeReview(testReview);
        mockMvc.perform(post("/write/store-review").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testReview))).andExpect(status().isInternalServerError());

    }

}