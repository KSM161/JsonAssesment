package com.assignment.Signify.controller;

import com.assignment.Signify.service.StatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatController.class)
class StatControllerTest {
    @MockBean
    StatService statService;

    @Autowired
    MockMvc mockMvc;
    //{"iTunes":0.0,"GooglePlayStore":0.0}
    @Test
    void shouldReturnMonthlyAverageonSucces() throws Exception {

        Map<String,Double> serviceResult = new HashMap<>();
        serviceResult.put("iTunes",2.8333333333333335);
        serviceResult.put("GooglePlayStore",2.869565217391304);
        when(statService.getMonthlyAverage(2018,02)).thenReturn(Optional.of(serviceResult));


        mockMvc.perform(get("/stat/ratings-average?year={year}&&month={month}",2018,2).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.iTunes").value(2.8333333333333335)).
                andExpect(jsonPath("$.GooglePlayStore").value(2.869565217391304));
    }
    @Test
    void shouldReturnNotFoundOnNullReturnFromGetMonthlyAverageMethod() throws Exception {
        when(statService.getMonthlyAverage(2018,02)).thenReturn(Optional.ofNullable(null));
        mockMvc.perform(get("/stat/ratings-average?year={year}&&month={month}",2018,2).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());

    }
    @Test
    void shouldThrowExceptionOnInvalidMonthFilter() throws Exception{
        String exception = List.of("value is out of range for paramter month. Accepted Range is 1-12").toString();
        mockMvc.perform(get("/stat/ratings-average?year={year}&&month={month}",2018,-1).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertEquals(exception,result.getResolvedException().getMessage()));
    }
    @Test
    void shouldThrowExceptionOnInvalidYearFilter() throws Exception{
        String exception = List.of("value for the Year is Mandatory ").toString();
        mockMvc.perform(get("/stat/ratings-average?year={year}&&month={month}",-1,12).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertEquals(exception,result.getResolvedException().getMessage()));
    }

    @Test
    void shouldReturnRatingsCountonSuccess() throws Exception{
        when(statService.getTotalRatingsInEachCatagory()).thenReturn(Optional.of(new HashMap<>()));
        mockMvc.perform(get("/stat/ratings-count").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }
    @Test
    void shouldReturnNotFoundOnNullReturnFromGetTotalRatingsInEachCatagoryMethod() throws Exception{
        when(statService.getTotalRatingsInEachCatagory()).thenReturn(Optional.ofNullable(null));
        mockMvc.perform(get("/stat/ratings-count").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

}