package rocks.shumyk.photo.app.api.users.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectsConfiguration {

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
}
