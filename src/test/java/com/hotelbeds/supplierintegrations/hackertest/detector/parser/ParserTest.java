package com.hotelbeds.supplierintegrations.hackertest.detector.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogEntry.LoginAction;
import com.hotelbeds.supplierintegrations.hackertest.detector.parser.LogEntryParserException;
import com.hotelbeds.supplierintegrations.hackertest.detector.parser.LogParser;

class ParserTest {
	
    private LogParser logParser = new LogParser();


    @Test
    void nullLine_throwsException() {

        assertThrows(LogEntryParserException.class, () -> logParser.parse(null));
    }

    @Test
     void emptyLine_throwsException() {

        assertThrows(LogEntryParserException.class, () -> logParser.parse(""));
    }

    @Test
    void wrongLineLength_throwsException() {

        assertThrows(LogEntryParserException.class, () -> logParser.parse("80.238.9.179,133612947,SIGNIN_FAILURE"));
    }

    @Test
    void invalidEpochFormat_throwsException() {

        assertThrows(LogEntryParserException.class, () -> logParser.parse("80.238.9.179,NOPENOPE,SIGNIN_FAILURE,Will.Smith"));
    }

    @Test
    void invalidActionFormat_throwsException() {

        assertThrows(LogEntryParserException.class, () -> logParser.parse("80.238.9.179,133612947,NOPENOPE,Will.Smith"));
    }

    @Test
    void validLog_returnParsedLogEntry() {
    	
    	//WHEN
    	LogEntry logEntry = logParser.parse("80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith");
    	
    	//THEN
    	assertEquals("80.238.9.179",logEntry.getIpAddress());
    	assertEquals(LocalDateTime.parse("1974-03-27T11:42:27"),logEntry.getDateTime());
    	assertEquals(LoginAction.SIGNIN_SUCCESS,logEntry.getLoginAction());
    	assertEquals("Will.Smith",logEntry.getUserName());

    }

}
