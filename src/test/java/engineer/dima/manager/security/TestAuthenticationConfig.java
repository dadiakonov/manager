package engineer.dima.manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import reactor.core.publisher.Mono;

@Configuration
public class TestAuthenticationConfig {
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return Mono::just;
    }

    @Bean
    public ServerAuthenticationConverter serverAuthenticationConverter() {
        return exchange -> Mono.empty();
    }
}
