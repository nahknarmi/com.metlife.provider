package com.metlife.provider;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Builder()
@Getter
public class Greeting {
    private final @NonNull Long id;
    private final @NonNull String content;
}
