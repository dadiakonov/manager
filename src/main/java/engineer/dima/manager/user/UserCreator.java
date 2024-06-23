package engineer.dima.manager.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserCreator {
    private final UserRepository userRepository;

    public UserCreator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> create(User user) {

        return userRepository.save(user);
    }
}
