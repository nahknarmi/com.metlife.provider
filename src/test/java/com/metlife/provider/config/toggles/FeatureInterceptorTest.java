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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeatureInterceptorTest {

    @Mock
    private FeatureRepository featureRepository;

    @Mock
    private FeatureToggle featureToggle;

    @Mock
    private HandlerMethod handlerMethod;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenFeatureIsOffButItsOffThenPassThrough() throws Exception {
        //given
        String featureName = "feature.A";
        when(featureRepository.isOn(featureName)).thenReturn(FALSE);

        when(featureToggle.feature()).thenReturn(featureName);
        when(handlerMethod.getMethodAnnotation(FeatureToggle.class)).thenReturn(featureToggle);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        FeatureInterceptor interceptor = new FeatureInterceptor(featureRepository);
        boolean continuesThroughChain = interceptor.preHandle(request, response, handlerMethod);

        //then
        assertThat(continuesThroughChain, is(true));
    }

    @Test
    public void givenFeatureIsOnAndItsOffThenInterceptedWith404() throws Exception {
        //given
        String featureName = "feature.A";
        when(featureRepository.isOn(featureName)).thenReturn(TRUE);

        when(featureToggle.feature()).thenReturn(featureName);
        when(handlerMethod.getMethodAnnotation(FeatureToggle.class)).thenReturn(featureToggle);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

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

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when
        FeatureInterceptor interceptor = new FeatureInterceptor(featureRepository);
        boolean continuesThroughChain = interceptor.preHandle(request, response, handlerMethod);

        //then
        assertThat(continuesThroughChain, is(true));
    }

}