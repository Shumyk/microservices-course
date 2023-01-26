package rocks.shumyk.photo.app.api.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import rocks.shumyk.photo.app.api.users.service.UsersService;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;
import rocks.shumyk.photo.app.api.users.ui.model.LoginRequest;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper mapper;
    private final UsersService usersService;

    @Value("jwt.token.expiration.time.ms")
    private long jwtTokenExpirationTime;
    @Value("jwt.token.secret")
    private String jwtTokenSecret;

    public AuthenticationFilter(final AuthenticationManager authenticationManager,
                                final ObjectMapper mapper,
                                final UsersService usersService) {
        super(authenticationManager);
        this.mapper = mapper;
        this.usersService = usersService;
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) throws AuthenticationException {
        final LoginRequest creds = mapper.readValue(request.getInputStream(), LoginRequest.class);
        return getAuthenticationManager()
                .authenticate(
                        UsernamePasswordAuthenticationToken.authenticated(
                                creds.getEmail(),
                                creds.getPassword(),
                                List.of())
                );
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authResult) throws IOException, ServletException {
        final String username = ((User) authResult.getPrincipal()).getUsername();
        final UserDTO userDetails = usersService.getUserDetailsByEmail(username);

        final String token = Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setExpiration(expirationTime())
                .signWith(SignatureAlgorithm.HS512, jwtTokenSecret)
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", Long.toString(userDetails.getId()));
    }

    private Date expirationTime() {
        return new Date(System.currentTimeMillis() + jwtTokenExpirationTime);
    }
}
