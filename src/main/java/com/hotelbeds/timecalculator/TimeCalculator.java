package com.hotelbeds.timecalculator;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public final class TimeCalculator {
	
    private static final String RFC_2822 = "EEE, dd MMM yyyy HH:mm:ss Z";
	
	private TimeCalculator() {
	}
    
    public static long minutesBetween(String time1, String time2) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(RFC_2822, Locale.US);

        ZonedDateTime parsedTime1 = ZonedDateTime.parse(time1, formatter);
        ZonedDateTime parsedTime2 = ZonedDateTime.parse(time2, formatter);
        
        return Math.abs(ChronoUnit.MINUTES.between(parsedTime1,parsedTime2));
    }
}
