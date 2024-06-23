package engineer.dima.manager.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserReader {
    private final UserRepository userRepository;

    public UserReader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
