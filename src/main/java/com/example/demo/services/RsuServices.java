package com.example.demo.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.HolidayRepository;
import com.example.demo.model.RSUMessage;

@Service
public class RsuServices {
    @Autowired
    HolidayRepository holidayRepository;


    public void initRSU (DateTime startDate, Integer duration, String timeUnit){

		RSUMessage rsuMessage = new RSUMessage();
		rsuMessage.setDuration(new StringBuilder(duration.toString()).append(" ").append(timeUnit).toString());
		LocalDateTime startdt = startDate.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		rsuMessage.setStartDate(startdt);

		LocalDateTime targetedEndDateByDuration = null;
		double holidayCount = 0;
		if (timeUnit.equalsIgnoreCase(TimeUnit.DAYS.name())) {
			targetedEndDateByDuration = startdt.plusDays(duration);

			//holiday count here, will affect targetedEndDateByDuration
			LocalDateTime tempDateTime = startdt.with(LocalTime.of(0, 0)).plusDays(duration);
			holidayCount = holidayRepository.getHolidayCount(startdt.toLocalDate(), tempDateTime.toLocalDate());
			while(holidayCount > 0) {
				boolean halfDay = holidayCount % 1 != 0;
				LocalDateTime tempNewDate = targetedEndDateByDuration.plusDays((int)holidayCount);
				//find holiday between (end date + holiday + 1, end date + holiday);
				holidayCount = holidayRepository.getHolidayCount(targetedEndDateByDuration.toLocalDate().plusDays(1), tempNewDate.toLocalDate());
				targetedEndDateByDuration = halfDay ? tempNewDate.plusHours(12) : tempNewDate;

			}
		}
		else if (timeUnit.equalsIgnoreCase(TimeUnit.HOURS.name())) {
			targetedEndDateByDuration = startdt.plusHours(duration);
		}
		else if (timeUnit.equalsIgnoreCase(TimeUnit.MINUTES.name())) {
			targetedEndDateByDuration = startdt.plusMinutes(duration);
		}
		rsuMessage.setCalculatedEndDate(targetedEndDateByDuration);

		Duration processingTime = Duration.between(LocalDateTime.now(), rsuMessage.getCalculatedEndDate());

		if (timeUnit.equalsIgnoreCase(TimeUnit.DAYS.name())) {
			rsuMessage.setProcessingTime(processingTime.toDays());
			rsuMessage.setProcessingTimeUnit(TimeUnit.DAYS);
		}
		else if (timeUnit.equalsIgnoreCase(TimeUnit.HOURS.name())) {
			rsuMessage.setProcessingTime(processingTime.toHours());
			rsuMessage.setProcessingTimeUnit(TimeUnit.HOURS);
		}
		else if (timeUnit.equalsIgnoreCase(TimeUnit.MINUTES.name())) {
			rsuMessage.setProcessingTime(processingTime.toMinutes());
			rsuMessage.setProcessingTimeUnit(TimeUnit.MINUTES);
		}

		if (rsuMessage.getProcessingTime()>0) { 
			rsuMessage.setEnableButton(Boolean.FALSE);
		} else {
			rsuMessage.setEnableButton(Boolean.TRUE);
		}

        System.out.println("Now is "+LocalDateTime.now()+"  RSUMessage: "+rsuMessage.toString());
}
}
