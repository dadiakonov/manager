package engineer.dima.manager.auth.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JWTAuthenticationManagerTest {
    private static final String TOKEN = "token";
    private static final String USERNAME = "username";

    @Mock
    private JWTValidator jwtValidator;
    private JWTAuthenticationToken jwtAuthenticationToken;

    private JWTAuthenticationManager jwtAuthenticationManager;

    @BeforeEach
    public void setUp() {
        jwtAuthenticationManager = new JWTAuthenticationManager(jwtValidator);
        jwtAuthenticationToken = new JWTAuthenticationToken(TOKEN, USERNAME);
    }

    @Test
    public void testAuthenticateReturnsAuthenticatedTokenWhenTokenIsValid() {
        when(jwtValidator.isValid(TOKEN, USERNAME))
                .thenReturn(true);

        Mono<Authentication> authenticationMono = jwtAuthenticationManager.authenticate(jwtAuthenticationToken);

        StepVerifier.create(authenticationMono)
                .assertNext(authentication -> assertTrue(authentication.isAuthenticated()))
                .verifyComplete();
    }

    @Test
    public void testAuthenticateThrowsJWTAuthenticationExceptionWhenTokenIsInvalid() {
        when(jwtValidator.isValid(TOKEN, USERNAME))
                .thenReturn(false);

        Mono<Authentication> authenticationMono = jwtAuthenticationManager.authenticate(jwtAuthenticationToken);

        StepVerifier.create(authenticationMono)
                .expectError(JWTAuthenticationException.class)
                .verify();
    }
}
