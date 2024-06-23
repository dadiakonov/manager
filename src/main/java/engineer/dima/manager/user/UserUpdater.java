package engineer.dima.manager.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserUpdater {
    private final UserRepository userRepository;

    public UserUpdater(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> update(User user) {
        return userRepository.save(user);
    }
}
