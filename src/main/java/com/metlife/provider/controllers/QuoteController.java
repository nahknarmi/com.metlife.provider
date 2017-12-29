package com.metlife.provider.controllers;


import com.metlife.provider.domain.Quote;
import com.metlife.provider.resources.QuoteResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@RestController
public class QuoteController {
    private final AtomicLong counter = new AtomicLong();
    private final Map<Long, Quote> quotes = new ConcurrentHashMap<>();

    @RequestMapping(path = "/quotes", method = RequestMethod.GET)
    public Resources<QuoteResource> quotes() {
        return new Resources<>(
                quotes.values().stream()
                        .map(QuoteResource::new)
                        .collect(Collectors.toList())
        );
    }

    @RequestMapping(path = "/quotes", method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid @RequestBody Quote quote) {
        long quoteId = counter.incrementAndGet();
        quotes.put(quoteId, Quote.builder().description(quote.getDescription()).id(quoteId).build()
        );

        Link self = new QuoteResource(quote).getLink("self");

        return ResponseEntity.created(URI.create(self.getHref())).build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/quotes/{quoteId}")
    public QuoteResource quote(@PathVariable Long quoteId) {
        return ofNullable(quotes.get(quoteId))
                .map(quote -> new QuoteResource(quotes.get(quoteId)))
                .orElseThrow(NotFoundException::new);
    }
}
