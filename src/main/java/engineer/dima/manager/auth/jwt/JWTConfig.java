package engineer.dima.manager.auth.jwt;

import engineer.dima.manager.crypto.KeyConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@EnableConfigurationProperties({ JWTProperties.class })
public class JWTConfig {
    @Bean
    public SecretKey secretKey(JWTProperties jwtProperties) {
        return KeyConverter.convertStringToSecretKey(jwtProperties.secret(), jwtProperties.algorithm());
    }
}
