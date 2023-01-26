package rocks.shumyk.photoapp.api.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GlobalFiltersConfiguration {

    @Bean
    public GlobalFilter secondPreFilter() {
        return (exchange, chain) -> {
            log.info("Second Pre-Filter is executed in configuration class.");
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> log.info("Second Post-Filter is executed in configuration class.")));
        };
    }

    @Bean
    public GlobalFilter thirdPreFilter() {
        return (exchange, chain) -> {
            log.info("Third Pre-Filter is executed in configuration class.");
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> log.info("Third Post-Filter is executed in configuration class.")));
        };
    }
}
