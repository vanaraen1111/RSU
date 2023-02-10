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
		initRsu(dt, 3, "DAYS");

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

	static class RSUMessage {
		LocalDateTime startDate;
		LocalDateTime calculatedEndDate;
		Integer holidayCount = 0;
		Long processingTime;
		TimeUnit processingTimeUnit;
		String duration;
		Boolean enableButton;

		public RSUMessage() {
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
        public Integer getHolidayCount() {
			return holidayCount;
		}
		public void setHolidayCount(Integer holidayCount) {
			this.holidayCount = holidayCount;
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

		
		@Override
		public String toString() {
			return "RSUMessage [startDate=" + startDate + ", calculatedEndDate=" + calculatedEndDate + ", holidayCount="
					+ holidayCount + ", processingTime=" + processingTime + ", processingTimeUnit=" + processingTimeUnit
					+ ", duration=" + duration + ", enableButton=" + enableButton + "]";
		}


	}

}
