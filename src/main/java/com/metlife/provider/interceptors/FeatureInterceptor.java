package com.metlife.provider.interceptors;

import com.metlife.provider.config.toggles.FeatureRepository;
import com.metlife.provider.config.toggles.FeatureToggle;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.Boolean.FALSE;

public class FeatureInterceptor implements HandlerInterceptor {
    private final FeatureRepository featureRepository;

    public FeatureInterceptor(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler)
            throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        FeatureToggle methodAnnotation = handlerMethod.getMethodAnnotation(FeatureToggle.class);
        if (methodAnnotation == null) {
            return true;
        }

        Boolean isOn = featureRepository.isOn(methodAnnotation.feature());
        if (isOn.equals(methodAnnotation.expectedToBeOn())) {
            return true;
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
