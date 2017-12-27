package com.metlife.provider.resources;

import com.metlife.provider.controllers.QuoteController;
import com.metlife.provider.domain.Quote;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class QuoteResource extends ResourceSupport {
    private final Quote quote;

    public QuoteResource(Quote quote) {
        this.quote = quote;
//        this.add(new Link(quote));
        this.add(linkTo(QuoteController.class).withRel("quotes"));
        this.add(linkTo(methodOn(QuoteController.class).create(quote)).withSelfRel());
    }

    public Quote getQuote() {
        return quote;
    }
}
