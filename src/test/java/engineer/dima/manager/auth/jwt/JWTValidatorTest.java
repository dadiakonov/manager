package engineer.dima.manager.auth.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JWTValidatorTest {
    private static final String USERNAME = "username";
    private static final String VALID_TOKEN = "validToken";
    private static final Long TTL = 3600000L; // 1 hour in milliseconds

    private JWTValidator jwtValidator;
    private JWTExtractor jwtExtractor;

    @BeforeEach
    public void setUp() {
        jwtExtractor = mock(JWTExtractor.class);
        jwtValidator = new JWTValidator(jwtExtractor);
    }

    @Test
    public void testIsValidWithValidToken() {
        when(jwtExtractor.extractUsername(VALID_TOKEN))
                .thenReturn(USERNAME);
        when(jwtExtractor.extractExpiration(VALID_TOKEN))
                .thenReturn(new Date(System.currentTimeMillis() + TTL));

        assertTrue(jwtValidator.isValid(VALID_TOKEN, USERNAME));
    }

    @Test
    public void testIsInvalidWithInvalidUsername() {
        when(jwtExtractor.extractUsername(VALID_TOKEN))
                .thenReturn("invalid-user");
        when(jwtExtractor.extractExpiration(VALID_TOKEN))
                .thenReturn(new Date(System.currentTimeMillis() + TTL));

        assertFalse(jwtValidator.isValid(VALID_TOKEN, USERNAME));
    }

    @Test
    public void testIsInvalidWithExpiredToken() {
        when(jwtExtractor.extractUsername(VALID_TOKEN))
                .thenReturn(USERNAME);
        when(jwtExtractor.extractExpiration(VALID_TOKEN))
                .thenReturn(new Date(System.currentTimeMillis() - 1000));

        assertFalse(jwtValidator.isValid(VALID_TOKEN, USERNAME));
    }
}
