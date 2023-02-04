package rocks.shumyk.photoapp.api.gateway;

import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final Environment env;

    public AuthorizationHeaderFilter(final Environment env) {
        super(Config.class);
        this.env = env;
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
                    .setSigningKey(env.getProperty("jwt.token.secret"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

            log.info("JWT Subject: {} with token: {}", subject, env.getProperty("jwt.token.secret"));
            return !Strings.isNullOrEmpty(subject);
        } catch (Exception e) {
            log.error("Exception during JWT decode [{}]: {}", env.getProperty("jwt.token.secret"), e.getMessage(), e);
            return false;
        }
    }


    public static class Config {
        // put configuration properties here
    }
}
