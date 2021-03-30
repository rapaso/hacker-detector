package com.hotelbeds.supplierintegrations.hackertest.detector.parser;

public class LogEntryParserException extends RuntimeException {

	private static final long serialVersionUID = -3478811161176997650L;
	
	public LogEntryParserException(String message) {
        super(message);
    }
	
    public LogEntryParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
