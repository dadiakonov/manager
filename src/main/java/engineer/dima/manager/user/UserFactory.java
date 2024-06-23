package engineer.dima.manager.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserFactory {
    private final PasswordEncoder passwordEncoder;

    public UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UUID userId, UserRequest userRequest) {
        String encodedPassword = passwordEncoder.encode(userRequest.password());

        return new User(userId, userRequest.email(), encodedPassword, userRequest.name());
    }

    public User create(UserRequest userRequest) {

        return create(null, userRequest);
    }
}
