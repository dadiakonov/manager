package engineer.dima.manager.auth.jwt;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

@Getter
public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private final String username;


    public JWTAuthenticationToken(String token, String username) {
        super(null);
        this.token = token;
        this.username = username;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    public Authentication withAuthenticated() {
        JWTAuthenticationToken cloned = new JWTAuthenticationToken(token, username);
        cloned.setAuthenticated(true);

        return cloned;
    }
}
