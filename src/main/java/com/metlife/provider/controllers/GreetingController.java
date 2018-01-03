package com.metlife.provider.controllers;

import com.metlife.provider.Greeting;
import com.metlife.provider.infra.config.AppConfig;
import com.metlife.provider.infra.config.toggles.FeatureToggle;
import com.metlife.provider.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Optional.ofNullable;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

    @RequestMapping(value = "/greeting", method = GET)
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException {
        return Greeting.builder()
                .content(String.format("Hello, %s!", name))
                .id(counter.incrementAndGet())
                .build();
    }

    @RequestMapping(value = "/toggle", method = GET)
    @FeatureToggle(feature = "feature.toggleA")
    public Map<String, String> toggledFeature() {
        return of("toggle", "true", "name", appConfig.getName());
    }

    @RequestMapping(value = "/foo", method = GET)
    public Greeting greetings() {
        return greetingService.greetings();
    }

    @RequestMapping(value = "/kube", method = GET)
    public Map<String, String> kube() {
        return of(
                "nodeName", ofNullable(System.getenv("MY_NODE_NAME")).orElse("unavailable"),
                "podName", ofNullable(System.getenv("MY_POD_NAME")).orElse("unavailable"),
                "podIp", ofNullable(System.getenv("MY_POD_IP")).orElse("unavailable"),
                "podServiceAccount", ofNullable(System.getenv("MY_POD_SERVICE_ACCOUNT")).orElse("unavailable")
                );
    }
}
