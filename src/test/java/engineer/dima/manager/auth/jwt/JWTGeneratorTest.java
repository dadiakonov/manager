package engineer.dima.manager.auth.jwt;

import engineer.dima.manager.crypto.KeyConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class JWTGeneratorTest {
    private static final String SECRET_KEY_AS_STRING = "hlmPHKJg+mvgDd+y9OHyrLzf8nKQJM04UMWkcOKETQo=";
    private static final String ALGORITHM = "HmacSHA256";
    private static final Long TTL = 3600000L; // 1 hour in milliseconds
    private static final String USERNAME = "username";

    private JWTGenerator jwtGenerator;
    private SecretKey secretKey;

    @BeforeEach
    public void setUp() {
        secretKey = KeyConverter.convertStringToSecretKey(SECRET_KEY_AS_STRING, ALGORITHM);

        JWTProperties jwtProperties = Mockito.mock(JWTProperties.class);
        when(jwtProperties.ttl()).thenReturn(TTL);

        jwtGenerator = new JWTGenerator(secretKey, jwtProperties);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtGenerator.generateToken(USERNAME);

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertEquals(USERNAME, claims.getSubject());
        assertTrue(System.currentTimeMillis() <= claims.getExpiration().getTime());
    }
}
