package com.example.demo;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.services.RsuServices;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private RsuServices rsuServices;

    @Override
    public void run(String...args) throws Exception {
        //rsuServices.initRSU(new DateTime("2023-02-07T08:00"), 20, "DAYS");
       // System.out.println("Id - 1 count:" + rsuServices.retrieveCountByCalendarId(1));
       //System.out.println("Id - null count:" + rsuServices.retrieveCountByCalendarId(null));
       System.out.println("Temporary Column - null count:" + rsuServices.retrieveCountByTemporaryColumn(null));
    }
}