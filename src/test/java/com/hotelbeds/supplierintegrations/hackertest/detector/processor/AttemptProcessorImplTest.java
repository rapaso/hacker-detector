package com.hotelbeds.supplierintegrations.hackertest.detector.processor;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogEntry.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.detector.processor.AttemptProcessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes={com.hotelbeds.supplierintegrations.hackertest.detector.processor.AttemptProcessorImpl.class})
class AttemptProcessorImplTest {

    //private AttemptProcessor attemptProcessor = new AttemptProcessorImpl();
	@Autowired
	private AttemptProcessor attemptProcessor;
	
    @BeforeEach
    void setup() {
    	attemptProcessor.init();
    }

    @Test
     void oneFailedLogEntryReturnFalse() {
    	
        //WHEN
    	LogEntry logEntry = new LogEntry("80.238.9.179", LocalDateTime.parse("1974-03-27T11:42:27"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
        String alert = attemptProcessor.getAlert(logEntry);

        //THEN
        assertNull(alert);
    }

    @Test
    void oneSuccessfulLogEntryReturnFalse() {

        //WHEN
    	LogEntry logEntry = new LogEntry("80.238.9.179", LocalDateTime.parse("1974-03-27T11:42:27"), LoginAction.SIGNIN_SUCCESS, "Will.Smith");
        String alert = attemptProcessor.getAlert(logEntry);
        
        //THEN
        assertNull(alert);
    }

    @Test
    void fourFailedLogEntriesFromSameIpWithinFiveMinutesReturnFalse() {
    	
        //WHEN
    	LogEntry logEntry1 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:00:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry2 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:01:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry3 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:02:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry4 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:03:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry5 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:05:01"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	
    	String alert1 = attemptProcessor.getAlert(logEntry1);
    	String alert2 = attemptProcessor.getAlert(logEntry2);
    	String alert3 = attemptProcessor.getAlert(logEntry3);
    	String alert4 = attemptProcessor.getAlert(logEntry4);
    	String alert5 = attemptProcessor.getAlert(logEntry5);

        //THEN
        assertNull(alert1);
        assertNull(alert2);
        assertNull(alert3);
        assertNull(alert4);
        assertNull(alert5);
    }

    @Test
    void fiveFailedLogEntriesFromSameIpWithinFiveMinutesReturnTrue() {
    	
        //WHEN
    	LogEntry logEntry1 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:00:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry2 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:01:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry3 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:02:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry4 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:03:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry5 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:05:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	
    	String alert1 = attemptProcessor.getAlert(logEntry1);
    	String alert2 = attemptProcessor.getAlert(logEntry2);
    	String alert3 = attemptProcessor.getAlert(logEntry3);
    	String alert4 = attemptProcessor.getAlert(logEntry4);
    	String alert5 = attemptProcessor.getAlert(logEntry5);

        //THEN
        assertNull(alert1);
        assertNull(alert2);
        assertNull(alert3);
        assertNull(alert4);
        assertEquals(alert5, "80.238.9.179");
    }

    @Test
    void fiveFailedLogEntriesFromDifferentIpsWithinFiveMinutesReturnFalse() {

        //WHEN
    	LogEntry logEntry1 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:00:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry2 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:01:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry3 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:02:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry4 = new LogEntry("80.238.9.179", LocalDateTime.parse("2021-03-27T00:03:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	LogEntry logEntry5 = new LogEntry("127.0.0.1", LocalDateTime.parse("2021-03-27T00:05:00"), LoginAction.SIGNIN_FAILURE, "Will.Smith");
    	
    	String alert1 = attemptProcessor.getAlert(logEntry1);
    	String alert2 = attemptProcessor.getAlert(logEntry2);
    	String alert3 = attemptProcessor.getAlert(logEntry3);
    	String alert4 = attemptProcessor.getAlert(logEntry4);
    	String alert5 = attemptProcessor.getAlert(logEntry5);

        //THEN
        assertNull(alert1);
        assertNull(alert2);
        assertNull(alert3);
        assertNull(alert4);
        assertNull(alert5);
    }
    

}
