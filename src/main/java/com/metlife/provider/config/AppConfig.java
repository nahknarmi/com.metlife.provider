package com.metlife.provider.config;


import com.metlife.provider.config.toggles.FeatureRepository;
import com.metlife.provider.interceptors.FeatureInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Validated
@Configuration
@ConfigurationProperties(prefix = "server")
public class AppConfig  extends WebMvcConfigurerAdapter {
    private String name;
    private String id;
    private String toggleA;

    @Autowired
    private Environment env;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToggleA() {
        return toggleA;
    }

    public void setToggleA(String toggleA) {
        this.toggleA = toggleA;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FeatureInterceptor(new FeatureRepository(env)));
        super.addInterceptors(registry);
    }
}
