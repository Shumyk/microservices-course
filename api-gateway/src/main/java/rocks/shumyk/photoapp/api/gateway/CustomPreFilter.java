package rocks.shumyk.photoapp.api.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomPreFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        log.info("Pre Filter is started.");

        final ServerHttpRequest request = exchange.getRequest();
        log.info("Request path: {}", request.getPath());

        request.getHeaders()
                .forEach((name, values) -> log.info("Header {} = {}", name, values));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
