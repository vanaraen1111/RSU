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
        rsuServices.initRSU( new DateTime("2023-02-07T08:00"), 20, "DAYS");
    }
}