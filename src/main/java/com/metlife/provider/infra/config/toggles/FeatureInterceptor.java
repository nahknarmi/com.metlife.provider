package com.metlife.provider.infra.config.toggles;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class FeatureInterceptor implements HandlerInterceptor {
    private final FeatureRepository featureRepository;

    FeatureInterceptor(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean resumeChain = true;
        Optional<FeatureToggle> featureToggleMethodAnnotation = extractFeatureToggleAnnotation(handler);

        if (featureToggleMethodAnnotation.isPresent() && featureIsTurnedOff(featureToggleMethodAnnotation.get())) {
            response.setStatus(SC_NOT_FOUND);
            resumeChain = false;
        }
        return resumeChain;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mAndV)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
            throws Exception {
    }

    private Optional<FeatureToggle> extractFeatureToggleAnnotation(Object handler) {
        return ofNullable(handler)
                .map(h -> (HandlerMethod) h)
                .map(hm -> hm.getMethodAnnotation(FeatureToggle.class));
    }

    private Boolean featureIsTurnedOff(FeatureToggle featureToggle) {
        return !featureRepository.isOn(featureToggle.feature()).orElse(false);
    }
}
