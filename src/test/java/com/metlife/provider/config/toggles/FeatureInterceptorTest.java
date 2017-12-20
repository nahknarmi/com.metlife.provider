package com.metlife.provider.config.toggles;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeatureInterceptorTest {
    private static final String FEATURE_NAME = "feature.A";

    @Mock
    private FeatureRepository featureRepository;

    @Mock
    private FeatureToggle featureToggle;

    @Mock
    private HandlerMethod handlerMethod;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void givenFeatureIsAnnotatedAndItsOnThenPassThrough() throws Exception {
        //given
        when(featureRepository.isOn(FEATURE_NAME)).thenReturn(Optional.of(TRUE));

        when(featureToggle.feature()).thenReturn(FEATURE_NAME);
        when(handlerMethod.getMethodAnnotation(FeatureToggle.class)).thenReturn(featureToggle);

        //when
        FeatureInterceptor interceptor = new FeatureInterceptor(featureRepository);
        boolean continuesThroughChain = interceptor.preHandle(request, response, handlerMethod);

        //then
        assertThat(continuesThroughChain, is(true));
    }

    @Test
    public void givenFeatureIsAnnotatedAndItsOffThenInterceptedWith404() throws Exception {
        //given
        when(featureRepository.isOn(FEATURE_NAME)).thenReturn(Optional.of(FALSE));

        when(featureToggle.feature()).thenReturn(FEATURE_NAME);
        when(handlerMethod.getMethodAnnotation(FeatureToggle.class)).thenReturn(featureToggle);

        //when
        FeatureInterceptor interceptor = new FeatureInterceptor(featureRepository);
        boolean continuesThroughChain = interceptor.preHandle(request, response, handlerMethod);

        //then
        assertThat(continuesThroughChain, is(false));
        assertThat(response.getStatus(), is(equalTo(404)));
    }

    @Test
    public void givenFeatureAnnotationIsNotPresentPassThrough() throws Exception {
        //given
        when(handlerMethod.getMethodAnnotation(FeatureToggle.class)).thenReturn(null);

        //when
        FeatureInterceptor interceptor = new FeatureInterceptor(featureRepository);
        boolean continuesThroughChain = interceptor.preHandle(request, response, handlerMethod);

        //then
        assertThat(continuesThroughChain, is(true));
    }

}