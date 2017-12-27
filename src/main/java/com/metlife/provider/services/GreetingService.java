package com.metlife.provider.services;

import com.metlife.provider.Greeting;
import com.metlife.provider.aop.Profiled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class GreetingService {
    private final AtomicLong counter = new AtomicLong();

    @Profiled
    public Greeting greetings() {
        return Greeting.builder()
                .id(counter.incrementAndGet())
                .content("Greetings")
                .build();
    }
}
