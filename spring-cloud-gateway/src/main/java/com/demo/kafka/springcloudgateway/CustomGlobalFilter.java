package com.demo.kafka.springcloudgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(CustomGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        // 🔹 Pre-filter logic
        log.info("Pre Filter: Request path = {}", exchange.getRequest().getPath());

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    // 🔹 Post-filter logic
                    log.info("Post Filter: Response status = {}", exchange.getResponse().getStatusCode());
                }));
    }

    @Override
    public int getOrder() {
        return -1; // Priority (lower = higher precedence)
    }
}

