package rocks.shumyk.photo.app.api.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity security) throws Exception {
        security
                .csrf().disable()

                .authorizeHttpRequests()
                .requestMatchers("/users/**")
                .permitAll();

        return security.build();
    }
}
