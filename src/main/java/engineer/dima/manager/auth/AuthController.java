package engineer.dima.manager.auth;

import engineer.dima.manager.auth.jwt.JWTGenerator;
import engineer.dima.manager.user.UserReader;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    public AuthController(UserReader userReader, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.userReader = userReader;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {

        return userReader.findByEmail(authRequest.email())
                .filter(user -> passwordEncoder.matches(authRequest.password(), user.password()))
                .map(user -> jwtGenerator.generateToken(user.email()))
                .map(AuthResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }
}
