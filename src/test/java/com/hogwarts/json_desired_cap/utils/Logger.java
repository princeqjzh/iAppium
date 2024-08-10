package com.hogwarts.json_desired_cap.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static String getPrtTime() {
        LocalDate today = LocalDate.now();
        LocalDate then = today.minusDays(2);
        LocalTime time_ago = LocalTime.now();
        LocalDateTime dt = LocalDateTime.of(then, time_ago);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.format(dt);
    }

    public static void info(String log) {
        System.out.println(getPrtTime() + " - INFO - " + log);
    }

    public static void error(String log) {
        System.out.println(getPrtTime() + " - ERROR - " + log);
    }

    public static void debug(String log) {
        System.out.println(getPrtTime() + " - DEBUG - " + log);
    }
}
