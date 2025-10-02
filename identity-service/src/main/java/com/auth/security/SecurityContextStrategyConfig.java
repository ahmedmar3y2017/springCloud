package com.auth.security;

import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextStrategyConfig {
    @PostConstruct
    public void init() {
        // Makes SecurityContext propagate to async/reactive contexts
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
