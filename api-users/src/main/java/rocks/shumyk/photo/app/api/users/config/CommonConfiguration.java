package rocks.shumyk.photo.app.api.users.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate rest() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper converter() {
        final ModelMapper converter = new ModelMapper();
        converter.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return converter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
