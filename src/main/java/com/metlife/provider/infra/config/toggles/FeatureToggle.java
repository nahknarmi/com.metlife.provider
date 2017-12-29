package com.metlife.provider.infra.config.toggles;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeatureToggle {
    String feature();
}
