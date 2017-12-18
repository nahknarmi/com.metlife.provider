package com.metlife.provider.controllers;

import com.metlife.provider.Greeting;
import com.metlife.provider.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();
    private final ApplicationProperties applicationProperties;

    @Autowired
    public GreetingController(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException {

        System.err.println(applicationProperties.getName());
        return new Greeting(counter.incrementAndGet(), String.format("Hello, %s!", name));
    }


}
