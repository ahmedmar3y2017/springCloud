package com.demo.kafka.springcloudgateway.filter;
import com.demo.kafka.springcloudgateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalAuthenticationFilter implements GlobalFilter, Ordered {
    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        log.info("AuthenticationFilter triggered, path={}", exchange.getRequest().getPath());

        if (validator.isSecured.test(exchange.getRequest())) {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("missing or invalid authorization header");
            }

            String token = authHeader.substring(7);
            if (jwtUtil.isTokenExpired(token)) {
                throw new RuntimeException("unauthorized access - token expired");
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
