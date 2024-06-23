package engineer.dima.manager.auth.jwt;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTValidator {
    private final JWTExtractor jwtExtractor;

    public JWTValidator(JWTExtractor jwtExtractor) {
        this.jwtExtractor = jwtExtractor;
    }

    public Boolean isValid(String token, String expectedUsername) {
        final String extractedUsername = jwtExtractor.extractUsername(token);

        return extractedUsername.equals(expectedUsername) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return jwtExtractor.extractExpiration(token).before(new Date());
    }
}
