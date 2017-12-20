package com.metlife.provider.config.toggles;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.ofNullable;

public class FeatureRepository {
    private static final String FEATURE_PREFIX = "feature.";
    private final Environment env;

    public FeatureRepository(Environment env) {
        this.env = env;
    }

    public Boolean isOn(String featureKey) {
        return ofNullable(allFeatures().get(featureKey)).orElse(FALSE);
    }

    private Set<String> featureKeys() {
        Map<String, Object> map = new HashMap<>();
        for (PropertySource<?> propertySource : ((AbstractEnvironment) env).getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
        return map.keySet().stream()
                .filter(k -> k.startsWith(FEATURE_PREFIX))
                .collect(Collectors.toSet());
    }

    private Map<String, Boolean> allFeatures() {
        return featureKeys().stream().collect(Collectors.toMap(k -> k, k -> Boolean.parseBoolean(env.getProperty(k))));
    }}
