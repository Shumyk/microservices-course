package rocks.shumyk.photo.app.api.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity security,
                                                   final AuthenticationFilter authenticationFilter) throws Exception {
        return security
                .csrf().disable()

                .authorizeHttpRequests()
                .anyRequest()
                .permitAll()

                .and()
                .addFilter(authenticationFilter)

                .headers().frameOptions().disable()

                .and()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(final ObjectMapper mapper,
                                                     final AuthenticationManager authenticationManager) {
        final AuthenticationFilter filter = new AuthenticationFilter(mapper);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
}
