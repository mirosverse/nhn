package com.nhnacademy.student;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFunction {
    public LocalDateTimeFunction() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatDate(LocalDateTime localDateTime, String pattern){
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

}