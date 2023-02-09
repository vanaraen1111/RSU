package com.example.demo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RsuMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsuMessageApplication.class, args);
		DateTime dt = new DateTime("2023-03-03") ;
		dt.withTime(8, 0, 0, 0);
		RSU(dt, 30, "DAYS");
	}

	static void RSU (DateTime startDate, Integer duration, String timeUnit) {
		LocalDateTime startdt = startDate.toDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
		System.out.println("start date : "+startdt);
		LocalDateTime targetedEndDateByDuration = null;
		if (timeUnit.equalsIgnoreCase(TimeUnit.DAYS.name())) {
			targetedEndDateByDuration = startdt.plusDays(duration);
		}

		System.out.println("targetedEndDateByDuration: "+targetedEndDateByDuration);
	}

}
