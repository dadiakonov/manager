package engineer.dima.manager.auth.jwt;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {
    private final JWTValidator jwtValidator;

    public JWTAuthenticationManager(JWTValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        return Mono.just(authentication)
                .cast(JWTAuthenticationToken.class)
                .filter(jwtToken -> jwtValidator.isValid(jwtToken.getToken(), jwtToken.getUsername()))
                .map(JWTAuthenticationToken::withAuthenticated)
                .switchIfEmpty(Mono.error(new JWTAuthenticationException()));
    }
}
