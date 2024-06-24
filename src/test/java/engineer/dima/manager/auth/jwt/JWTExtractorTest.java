package engineer.dima.manager.auth.jwt;

import engineer.dima.manager.crypto.KeyConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class JWTExtractorTest {
    private static final String SECRET_KEY_AS_STRING = "hlmPHKJg+mvgDd+y9OHyrLzf8nKQJM04UMWkcOKETQo=";
    private static final String ALGORITHM = "HmacSHA256";
    private static final String USERNAME = "username";
    private static final Long TTL = 3600000L; // 1 hour in milliseconds

    private JWTExtractor jwtExtractor;
    private SecretKey secretKey;

    @BeforeEach
    public void setUp() {
        secretKey = KeyConverter.convertStringToSecretKey(SECRET_KEY_AS_STRING, ALGORITHM);
        jwtExtractor = new JWTExtractor(secretKey);
    }

    @Test
    public void testExtractUsername() {
        String token = createTokenWithUsername();

        String extractedUsername = jwtExtractor.extractUsername(token);

        assertEquals(USERNAME, extractedUsername);
    }

    @Test
    public void testExtractExpiration() {
        Date expirationDate = new Date(System.currentTimeMillis() + TTL);
        String token = createTokenWithExpiration(expirationDate);

        Date extractedExpiration = jwtExtractor.extractExpiration(token);

        assertEquals(expirationDate.getTime() / 1000, extractedExpiration.getTime() / 1000);
    }

    @Test
    public void testExtractClaim() {
        String token = createTokenWithUsername();

        String extractedUsername = jwtExtractor.extractClaim(token, Claims::getSubject);

        assertEquals(USERNAME, extractedUsername);
    }

    @Test
    public void testExtractClaimThrowsJWTAuthenticationException() {

        assertThrows(JWTAuthenticationException.class, () -> jwtExtractor.extractClaim("invalid-token", Claims::getSubject), "Invalid token.");
    }

    private String createTokenWithUsername() {
        return Jwts.builder()
                .subject(USERNAME)
                .signWith(secretKey)
                .compact();
    }

    private String createTokenWithExpiration(Date expirationDate) {
        return Jwts.builder()
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }
}
