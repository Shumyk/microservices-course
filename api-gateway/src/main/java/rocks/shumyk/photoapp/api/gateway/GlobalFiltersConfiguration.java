package rocks.shumyk.photoapp.api.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GlobalFiltersConfiguration {

    @Bean
    @Order(1)
    public GlobalFilter secondPreFilter() {
        return (exchange, chain) -> {
            log.info("Second Pre-Filter is executed in configuration class.");
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> log.info("Second Post-Filter is executed in configuration class.")));
        };
    }

    @Bean
    @Order(2)
    public GlobalFilter thirdPreFilter() {
        return (exchange, chain) -> {
            log.info("Third Pre-Filter is executed in configuration class.");
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> log.info("First Post-Filter is executed in configuration class.")));
        };
    }
}
