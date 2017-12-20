package com.metlife.provider.config.toggles;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        if (featureOff(handler)) {
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

    private boolean featureOff(Object handler) {
        return ofNullable(handler)
                .map(h -> (HandlerMethod) h)
                .map(hm -> hm.getMethodAnnotation(FeatureToggle.class))
                .map(a -> featureRepository.isOn(a.feature()))
                .orElse(false);
    }
}
