package engineer.dima.manager.auth.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JWTServerAuthenticationConverterTest {
    private static final String TOKEN = "token";
    private static final String USERNAME = "username";
    private static final String AUTHORIZATION_HEADER = "Bearer " + TOKEN;

    @Mock
    private JWTExtractor jwtExtractor;
    @Mock
    private ServerWebExchange exchange;
    @Mock
    private ServerHttpRequest request;
    @Mock
    private HttpHeaders httpHeaders;

    private JWTServerAuthenticationConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new JWTServerAuthenticationConverter(jwtExtractor);

        when(exchange.getRequest())
                .thenReturn(request);
        when(request.getHeaders())
                .thenReturn(httpHeaders);
    }

    @Test
    public void testConvertReturnsAuthenticationWhenTokenIsValid() {
        when(httpHeaders.getFirst(HttpHeaders.AUTHORIZATION))
                .thenReturn(AUTHORIZATION_HEADER);
        when(jwtExtractor.extractUsername(TOKEN))
                .thenReturn(USERNAME);

        Mono<Authentication> authenticationMono = converter.convert(exchange);

        StepVerifier.create(authenticationMono)
                .assertNext(authentication -> assertEquals(USERNAME, authentication.getPrincipal()))
                .verifyComplete();
    }

    @Test
    public void testConvertReturnsEmptyWhenTokenIsMissing() {
        when(httpHeaders.getFirst(HttpHeaders.AUTHORIZATION))
                .thenReturn(null);

        Mono<Authentication> authenticationMono = converter.convert(exchange);

        StepVerifier.create(authenticationMono)
                .verifyComplete();
    }

    @Test
    public void testConvertReturnsEmptyWhenTokenDoesNotStartWithBearer() {
        when(httpHeaders.getFirst(HttpHeaders.AUTHORIZATION))
                .thenReturn(TOKEN);

        Mono<Authentication> authenticationMono = converter.convert(exchange);

        StepVerifier.create(authenticationMono)
                .verifyComplete();
    }
}
