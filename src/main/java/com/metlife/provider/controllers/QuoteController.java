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
        quote.setId(counter.incrementAndGet());
        quotes.put(quote.getId(), quote);

//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(quote.getId())
//                .toUri();

        Link self = new QuoteResource(quote).getLink("self");

        return ResponseEntity.created(URI.create(self.getHref())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/quotes/{quoteId}")
    public QuoteResource quote(@PathVariable Long quoteId) {
        return Optional.ofNullable(quotes.get(quoteId))
                .map(quote -> new QuoteResource(quotes.get(quoteId))).orElseThrow(NotFoundException::new);
    }
}
