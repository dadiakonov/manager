package engineer.dima.manager.auth.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@EnableConfigurationProperties({ JWTProperties.class })
public class JWTGenerator {
    private final SecretKey secretKey;
    private final JWTProperties jwtProperties;

    public JWTGenerator(SecretKey secretKey, JWTProperties jwtProperties) {
        this.secretKey = secretKey;
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.ttl()))
                .signWith(secretKey)
                .compact();
    }
}
