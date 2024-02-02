package com.assignment.Signify.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    private static final DateTimeFormatter reviewedDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
    private static final DateTimeFormatter datefilterFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

    public static boolean dateFilter(String datefilter, String reviewedDate) {
        LocalDate formattedReviewedDate = LocalDate.parse(reviewedDate, reviewedDateFormatter);
        LocalDate formattedDateFilter = LocalDate.parse(datefilter,datefilterFormatter);
        return  formattedDateFilter.isEqual(formattedReviewedDate);
    }

    public static boolean monthOfYearFilter(String inputDate , int year ,  int month){
        LocalDate date = LocalDate.parse(inputDate, reviewedDateFormatter);
        return date.getYear() == year && date.getMonthValue()==month;
    }


}
