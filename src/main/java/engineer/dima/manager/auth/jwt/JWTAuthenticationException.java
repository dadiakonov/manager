package engineer.dima.manager.auth.jwt;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {
    private static final String MESSAGE = "Invalid token.";

    public JWTAuthenticationException() {
        super(MESSAGE);
    }
}
