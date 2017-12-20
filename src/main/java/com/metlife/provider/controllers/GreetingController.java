package com.metlife.provider.controllers;

import com.google.common.collect.ImmutableMap;
import com.metlife.provider.Greeting;
import com.metlife.provider.config.AppConfig;
import com.metlife.provider.config.toggles.FeatureToggle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();
    private final AppConfig appConfig;

    @Autowired
    public GreetingController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException {
        return new Greeting(counter.incrementAndGet(), String.format("Hello, %s!", name));
    }

    @RequestMapping(value = "toggle")
    @FeatureToggle(feature = "feature.toggleA")
    public Map<String, String> toggledFeature() {
        return ImmutableMap.of("toggle", "true", "name", appConfig.getName());
    }
}
