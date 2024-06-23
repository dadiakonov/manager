package engineer.dima.manager.auth.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class JWTServerAuthenticationConverter implements ServerAuthenticationConverter {
    private static final String BEARER = "Bearer ";

    private final JWTExtractor jwtExtractor;

    public JWTServerAuthenticationConverter(JWTExtractor jwtExtractor) {
        this.jwtExtractor = jwtExtractor;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(BEARER))
                .map(header -> header.substring(BEARER.length()))
                .map(token -> new JWTAuthenticationToken(token, jwtExtractor.extractUsername(token)));
    }
}
