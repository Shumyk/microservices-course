package rocks.shumyk.photo.app.api.users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import rocks.shumyk.photo.app.api.users.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity security,
                                                   final AuthenticationFilter authenticationFilter,
                                                   final UsersService usersService) throws Exception {
        return security
                .csrf().disable()

                .userDetailsService(usersService)

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
}
