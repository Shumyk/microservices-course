package rocks.shumyk.photo.app.api.users.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import rocks.shumyk.photo.app.api.users.shared.UserDTO;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.token.expiration.time.ms}")
    private long jwtTokenExpirationTime;

    private final Environment env;

    public String generate(final UserDTO user) {
        final Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("userId", user.getId());

        log.info("Generating JWT for user {} with secret {}", user.getEmail(), env.getProperty("jwt.token.secret"));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationTime())
                .signWith(SignatureAlgorithm.HS512, env.getProperty("jwt.token.secret"))
                .compact();
    }

    public Optional<UserDTO> parse(final String token) {
        try {
            final Claims body = Jwts.parser()
                    .setSigningKey(env.getProperty("jwt.token.secret"))
                    .parseClaimsJws(token)
                    .getBody();

            return Optional.of(
                    UserDTO.builder()
                            .email(body.getSubject())
                            .id(body.get("userId", Long.class))
                            .build()
            );
        } catch (JwtException | ClassCastException e) {
            log.error("Error occurred during parsing JWT: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    private Date expirationTime() {
        return new Date(System.currentTimeMillis() + jwtTokenExpirationTime);
    }
}
