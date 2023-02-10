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
		DateTime dt = new DateTime("2023-02-08T08:00") ;
		initRsu(dt, 2, "DAYS");

	}

	static void initRsu (DateTime startDate, Integer duration, String timeUnit) {
		RSUMessage rsuMessage = new RSUMessage();
		rsuMessage.setDuration(new StringBuilder(duration.toString()).append(" ").append(timeUnit).toString());
		LocalDateTime startdt = startDate.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		rsuMessage.setStartDate(startdt);

		LocalDateTime targetedEndDateByDuration = null;
		if (timeUnit.equalsIgnoreCase(TimeUnit.DAYS.name())) {
			targetedEndDateByDuration = startdt.plusDays(duration);

			//holiday count here, will affect targetedEndDateByDuration
			double holidayCount = 0.5; //could be 0/1/0.5 also (which denote half day holiday)
			//currently putting this value more than 0 will cause infinite loop
			rsuMessage.setHolidayCount(holidayCount);
			//holidayCount = holidayRepository.getHolidayCount(responseInfo.getStartDate().toLocalDate(), tempDateTime.toLocalDate());
			while(holidayCount > 0) {
				boolean halfDay = holidayCount % 1 != 0;
				LocalDateTime tempNewDate = targetedEndDateByDuration.plusDays((int)holidayCount);
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
		double holidayCount = 0;
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
        public double getHolidayCount() {
			return holidayCount;
		}
		public void setHolidayCount(double holidayCount) {
			this.holidayCount = holidayCount;
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

		public Integer getProcessedTimeInMinutes() {
			return processedTimeInMinutes;
		}
		public void setProcessedTimeInMinutes(Integer processedTimeInMinutes) {
			this.processedTimeInMinutes = processedTimeInMinutes;
		}
		public String getProcessingTimeMessage() {
			return processingTimeMessage;
		}
		public void setProcessingTimeMessage(String processingTimeMessage) {
			this.processingTimeMessage = processingTimeMessage;
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
