package com.metlife.provider.domain;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;


@Builder()
@Getter
public class Quote {
    private Long id;
    @NotNull
    private String description;
}
