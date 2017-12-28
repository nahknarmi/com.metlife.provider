package com.metlife.provider.config.toggles;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

class FeatureRepository {
    private static final String FEATURE_PREFIX = "feature.";
    private final Environment env;

    FeatureRepository(Environment env) {
        this.env = env;
    }

    /**
     * Tests to see if a given feature exists and whether it is on or off.
     *
     * @param featureKey feature as specified in the application.properties file.
     * @return
     * Optional.absent() if feature does not exist,
     * Optional(true) if feature exists and is ON,
     * Optional(false) if feature exists and is OFF
     */
    Optional<Boolean> isOn(String featureKey) {
        return ofNullable(allFeatures().get(featureKey));
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
        return featureKeys()
                .stream()
                .collect(Collectors.toMap(k -> k, k -> Boolean.parseBoolean(env.getProperty(k))));
    }}
