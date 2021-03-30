package com.hotelbeds.supplierintegrations.hackertest.detector.processor;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogEntry;

public interface AttemptProcessor {

    String getAlert(LogEntry logEntry);
    
    void init();

}
