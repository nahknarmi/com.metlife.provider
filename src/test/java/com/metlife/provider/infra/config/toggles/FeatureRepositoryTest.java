package com.metlife.provider.infra.config.toggles;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Configuration
@ConfigurationProperties
public class FeatureRepositoryTest {
    private static final String FEATURE_KEY = "feature.foo";

    @Autowired
    private Environment env;

    @Test
    @Ignore
    public void isOn() throws Exception {
        FeatureRepository repository = new FeatureRepository(env);
        Optional<Boolean> isOn = repository.isOn(FEATURE_KEY);

        assertThat(isOn.isPresent(), is(true));
    }

}