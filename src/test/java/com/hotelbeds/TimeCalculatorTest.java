package com.hotelbeds;

import com.hotelbeds.timecalculator.TimeCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeCalculatorTest {

    @Test
    void sameTime_returnZero() {

        //GIVEN
        String timeStamp1 = "Thu, 25 Mar 2021 00:01:00 +0200";
        String timeStamp2 = "Thu, 25 Mar 2021 00:01:00 +0200";

        //WHEN
        long result = TimeCalculator.minutesBetween(timeStamp1, timeStamp2);

        //THEN
        assertEquals(0, result);
    }
    
    @Test
    void LessThanOneMinuteDifference_returnZero() {

        //GIVEN
        String timeStamp1 = "Thu, 21 Dec 2000 16:02:00 +0100";
        String timeStamp2 = "Thu, 21 Dec 2000 16:02:49 +0100";

        //WHEN
        long result = TimeCalculator.minutesBetween(timeStamp1, timeStamp2);

        //THEN
        assertEquals(0, result);
    }

    @Test
    void firstTimeStampIsOneMinuteSooner_returnOne() {

        //GIVEN
        String timeStamp1 = "Thu, 25 Mar 2021 00:01:00 +0200";
        String timeStamp2 = "Thu, 25 Mar 2021 00:02:00 +0200";

        //WHEN
        long result = TimeCalculator.minutesBetween(timeStamp1, timeStamp2);

        //THEN
        assertEquals(1, result);
    }

    @Test
    void SecondTimeStampIsOneMinuteSooner_returnOne() {

        //GIVEN
        String timeStamp1 = "Thu, 25 Mar 2021 00:02:00 +0200";
        String timeStamp2 = "Thu, 25 Mar 2021 00:01:00 +0200";

        //WHEN
        long result = TimeCalculator.minutesBetween(timeStamp1, timeStamp2);

        //THEN
        assertEquals(1, result);
    }

    @Test
    void sameTimeStampDifferentTimeZoneOneHourDifference_returnSixty() {

        //GIVEN
        String timeStamp1 = "Thu, 21 Dec 2000 16:02:00 +0100";
        String timeStamp2 = "Thu, 21 Dec 2000 16:02:00 +0200";

        //WHEN
        long result = TimeCalculator.minutesBetween(timeStamp1, timeStamp2);

        //THEN
        assertEquals(60, result);
    }

}
