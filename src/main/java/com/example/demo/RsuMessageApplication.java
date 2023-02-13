package com.example.demo;

import java.time.Duration;
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
		DateTime dt = new DateTime("2023-02-07T08:00") ;
		initRsu(dt, 20, "DAYS", 0.5); // holiday could be 1/0.5 also (which denote half day holiday)

	}

	static void initRsu (DateTime startDate, Integer duration, String timeUnit, double holidayCount) {
		RSUMessage rsuMessage = new RSUMessage();
		rsuMessage.setDuration(new StringBuilder(duration.toString()).append(" ").append(timeUnit).toString());
		LocalDateTime startdt = startDate.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		rsuMessage.setStartDate(startdt);

		LocalDateTime targetedEndDateByDuration = null;
		if (timeUnit.equalsIgnoreCase(TimeUnit.DAYS.name())) {
			targetedEndDateByDuration = startdt.plusDays(duration);

			//holiday count here, will affect targetedEndDateByDuration
			rsuMessage.setHolidayCount(holidayCount);
			//holidayCount = holidayRepository.getHolidayCount(responseInfo.getStartDate().toLocalDate(), tempDateTime.toLocalDate());
			while(holidayCount > 0) {
				boolean halfDay = holidayCount % 1 != 0;
				LocalDateTime tempNewDate = targetedEndDateByDuration.plusDays((int)holidayCount);
				//find holiday between (end date + holiday + 1, end date + holiday);
				//holidayCount = holidayRepository.getHolidayCount(responseInfo.getTargetedEndDateByDuration().toLocalDate().plusDays(1), tempNewDate.toLocalDate());
				//here the holidayCount is updated based on this query
				// select coalesce(sum(cast(atc."COUNT" as decimal)), 0)
				// from "ALZ360"."AZLM_TGE_CALENDAR" atc 
				// where atc."DATE" between '2022-02-08 00:00:00.000 +0800' and '2022-02-08 00:00:00.000 +0800'
				holidayCount -=1.0; //temporary code to emulate the 
				//find holiday between (end date + holiday + 1, end date + holiday);
				targetedEndDateByDuration = halfDay ? tempNewDate.plusHours(12) : tempNewDate;
				//System.out.println("checking");
			}
		}
		else if (timeUnit.equalsIgnoreCase(TimeUnit.HOURS.name())) {
			targetedEndDateByDuration = startdt.plusHours(duration);
		}
		else if (timeUnit.equalsIgnoreCase(TimeUnit.MINUTES.name())) {
			targetedEndDateByDuration = startdt.plusMinutes(duration);
		}
		rsuMessage.setCalculatedEndDate(targetedEndDateByDuration);
		rsuMessage.setProcessedTimeInMinutes(calculateProcessingTimeInMinutes(rsuMessage.getCalculatedEndDate()));
		if (rsuMessage.getProcessedTimeInMinutes()>0) { 
			rsuMessage.setEnableButton(Boolean.FALSE);
		} else {
			rsuMessage.setEnableButton(Boolean.TRUE);
		}
		//Conversion to days / hours / minutes for message displayed
		rsuMessage.setProcessingTimeMessage(minutesToDaysHoursMinutes(rsuMessage.getProcessedTimeInMinutes()));

		System.out.println("Now is "+LocalDateTime.now()+"  RSUMessage: "+rsuMessage.toString());
	}

	static String minutesToDaysHoursMinutes(int time) {
		if (time > 0) {
			Duration d = Duration.ofMinutes(time);
			long days = d.toDays();
			long hours = d.toHours() % 24;
			long minutes = d.toMinutes() % 60;
			return String.format("Please allow processing time of %d Day(s) %d Hour(s) %d Minute(s)", days, hours, minutes);
		} else {
			return ""; // no message as button is enabled at this point
		}

    }

	static Integer calculateProcessingTimeInMinutes (LocalDateTime endDate) {
		Duration processingTime = Duration.between(LocalDateTime.now(), endDate);

		//check difference in minutes
		return Math.toIntExact(processingTime.toMinutes());
	}

	static class RSUMessage {
		LocalDateTime startDate;
		LocalDateTime calculatedEndDate;
		double holidayCount;
		Long processingTime;
		TimeUnit processingTimeUnit;
		String duration;
		Boolean enableButton;
		Integer processedTimeInMinutes;
		String processingTimeMessage;

		public RSUMessage() {
			//
		}
		public LocalDateTime getStartDate() {
			return startDate;
		}
		public void setStartDate(LocalDateTime startDate) {
			this.startDate = startDate;
		}
		public LocalDateTime getCalculatedEndDate() {
			return calculatedEndDate;
		}
		public void setCalculatedEndDate(LocalDateTime calculatedEndDate) {
			this.calculatedEndDate = calculatedEndDate;
		}

		public Long getProcessingTime() {
			return processingTime;
		}
		public void setProcessingTime(Long processingTime) {
			this.processingTime = processingTime;
		}

		public TimeUnit getProcessingTimeUnit() {
			return processingTimeUnit;
		}
		public void setProcessingTimeUnit(TimeUnit processingTimeUnit) {
			this.processingTimeUnit = processingTimeUnit;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}        
		public Boolean getEnableButton() {
            return enableButton;
        }
        public void setEnableButton(Boolean enableButton) {
            this.enableButton = enableButton;
        }
		public double getHolidayCount() {
			return holidayCount;
		}
		public void setHolidayCount(double holidayCount) {
			this.holidayCount = holidayCount;
		}
		@Override
		public String toString() {
			return "RSUMessage [startDate=" + startDate + ", calculatedEndDate=" + calculatedEndDate + ", holidayCount="
					+ holidayCount + ", duration=" + duration + ", enableButton=" + enableButton
					+ ", processedTimeInMinutes=" + processedTimeInMinutes + ", processingTimeMessage="
					+ processingTimeMessage + "]";
		}

	}

}
