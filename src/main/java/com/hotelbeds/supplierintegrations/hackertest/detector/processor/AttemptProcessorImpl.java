package com.hotelbeds.supplierintegrations.hackertest.detector.processor;

import com.hotelbeds.supplierintegrations.hackertest.detector.model.LogEntry;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


import static com.hotelbeds.supplierintegrations.hackertest.detector.model.LogEntry.LoginAction.SIGNIN_SUCCESS;

@Component
@Configuration
public class AttemptProcessorImpl implements AttemptProcessor {

	DB db = DBMaker.memoryDB().make();
	HTreeMap<String, List<LocalDateTime>> failedLoginAttemptsByIp = db.hashMap("map").keySerializer(Serializer.STRING)
			.valueSerializer(Serializer.JAVA).createOrOpen();

	@Value("${hackerdetector.timeWindowInMinutes}")
	private int timeWindowInMinutes;

	@Value("${hackerdetector.maxAttempts}")
	private int maxAttempts;
	
	public void init() {
		failedLoginAttemptsByIp.clear();
	}

	@Override
	public String getAlert(LogEntry logEntry) {

		if (SIGNIN_SUCCESS.equals(logEntry.getLoginAction())) {
			return null;
		}

		List<LocalDateTime> attemptsByIp = failedLoginAttemptsByIp.getOrDefault(logEntry.getIpAddress(),
				new LinkedList<>());

		attemptsByIp.removeIf(item -> item.plusMinutes(timeWindowInMinutes).isBefore(logEntry.getDateTime()));
		attemptsByIp.add(logEntry.getDateTime());
		failedLoginAttemptsByIp.put(logEntry.getIpAddress(), attemptsByIp);
		
		return (attemptsByIp.size() >= maxAttempts ? logEntry.getIpAddress() : null);

	}

}
