package com.metlife.provider.controllers;

import com.metlife.provider.Greeting;
import com.metlife.provider.config.AppConfig;
import com.metlife.provider.config.toggles.FeatureToggle;
import com.metlife.provider.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.collect.ImmutableMap.of;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();
    private final AppConfig appConfig;
    private final GreetingService greetingService;

    @Autowired
    public GreetingController(AppConfig appConfig, GreetingService greetingService) {
        this.appConfig = appConfig;
        this.greetingService = greetingService;
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException {
        return Greeting.builder()
                .content(String.format("Hello, %s!", name))
                .id(counter.incrementAndGet())
                .build();
    }

    @RequestMapping(value = "/toggle", method = RequestMethod.GET)
    @FeatureToggle(feature = "feature.toggleA")
    public Map<String, String> toggledFeature() {
        return of("toggle", "true", "name", appConfig.getName());
    }

    @RequestMapping(value = "/foo", method = RequestMethod.GET)
    public Greeting greetings() {
        return greetingService.greetings();
    }

}
