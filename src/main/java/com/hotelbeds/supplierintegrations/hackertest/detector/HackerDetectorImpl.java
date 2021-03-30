package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.processor.AttemptProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogEntry;
import com.hotelbeds.supplierintegrations.hackertest.detector.parser.LogParser;


public class HackerDetectorImpl implements HackerDetector {

	@Autowired
    AttemptProcessor attemptProcessor;
    
    public String parseLine(String line) {
    	
    	LogParser logParser = new LogParser();
    	
        LogEntry logEntry = logParser.parse(line);
        return attemptProcessor.getAlert(logEntry);
    }


}
