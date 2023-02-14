package rocks.shumyk.photo.app.api.users.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends GenericFilterBean {

    public static final String BEARER = "Bearer ";

    private final JwtService jwt;

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain chain) throws IOException, ServletException {
        final var request = (HttpServletRequest) servletRequest;
        final var response = (HttpServletResponse) servletResponse;

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (null == authHeader || !authHeader.startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        final var authToken = getAuthenticationToken(authHeader);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(final String authHeader) {
        final String xToken = authHeader.replace(BEARER, "");
        return jwt.parse(xToken)
                .map(u -> UsernamePasswordAuthenticationToken.authenticated(u.getId(), null, List.of()))
                .orElse(null);
    }
}
