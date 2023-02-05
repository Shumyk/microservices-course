package rocks.shumyk.photoapp.api.albums.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectsConfiguration {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
