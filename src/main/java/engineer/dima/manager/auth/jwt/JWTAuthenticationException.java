package engineer.dima.manager.auth.jwt;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {
    private final static String MESSAGE = "Invalid token.";

    public JWTAuthenticationException() {
        super(MESSAGE);
    }
}
