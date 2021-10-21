package com.course.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoginAdminGatewayFilter implements GatewayFilter, Ordered {
    private static final Logger LOG = LoggerFactory.getLogger(LoginAdminGatewayFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // get token from headers
        String token = exchange.getRequest().getHeaders().getFirst("token");
        LOG.info("filter work: {}", token);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
