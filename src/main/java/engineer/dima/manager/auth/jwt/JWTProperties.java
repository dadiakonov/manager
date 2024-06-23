package engineer.dima.manager.auth.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jwt")
public record JWTProperties(
        @Value("${secret}")
        String secret,

        @Value("${algorithm}")
        String algorithm,

        @Value("${ttl}")
        Long ttl
) {
}
