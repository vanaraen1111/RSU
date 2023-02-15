package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class RSUMessage {
    LocalDateTime startDate;
    LocalDateTime calculatedEndDate;
    Long processingTime;
    TimeUnit processingTimeUnit;
    String duration;
    Boolean enableButton;
    RSUMessage rsu;


    public RSUMessage() {
        //
    }

    public RSUMessage(RSUMessage rsu) {
        this.rsu = rsu;
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

    @Override
    public String toString() {
        return "RSUMessage [startDate=" + startDate + ", calculatedEndDate=" + calculatedEndDate
                + ", processingTime=" + processingTime + ", processingTimeUnit=" + processingTimeUnit
                + ", duration=" + duration + ", enableButton=" + enableButton + "]";
    } 
}
