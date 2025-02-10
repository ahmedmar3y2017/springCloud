package com.frankmoley.lil.roomreservationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebFilterConfig implements WebMvcConfigurer {

    @Autowired
    IdempotencyInterceptor idempotencyInterceptor;
    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations and resource handler requests.
     * Interceptors can be registered to apply to all requests or be limited
     * to a subset of URL patterns.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(idempotencyInterceptor)
                .addPathPatterns("/**");
    }
}
