package rocks.shumyk.photoapp.api.gateway;

import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Value("${jwt.token.secret}")
    private String jwtTokenSecret;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return error(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            final String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            final String jwt = authHeader.replace("Bearer ", "");

            if (!isJwtValid(jwt)) {
                return error(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> error(final ServerWebExchange exchange, final String errorMessage, final HttpStatus status) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        final DataBuffer buffer = response.bufferFactory()
                .wrap(errorMessage.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

    private boolean isJwtValid(final String jwt) {
        try {
            final String subject = Jwts.parser()
                    .setSigningKey(jwtTokenSecret)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

            return !Strings.isNullOrEmpty(subject);
        } catch (Exception e) {
            return false;
        }
    }


    public static class Config {
        // put configuration properties here
    }
}
