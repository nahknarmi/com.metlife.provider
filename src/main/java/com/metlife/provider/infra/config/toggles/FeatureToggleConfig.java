package com.metlife.provider.infra.config.toggles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Validated
@Configuration
@ConfigurationProperties
public class FeatureToggleConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private Environment env;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FeatureInterceptor(new FeatureRepository(env)));
        super.addInterceptors(registry);
    }
}
