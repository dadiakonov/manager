package engineer.dima.manager.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserCreator {
    private final UserRepository userRepository;
    private final CreatedUserCounter createdUserCounter;

    public UserCreator(UserRepository userRepository, CreatedUserCounter createdUserCounter) {
        this.userRepository = userRepository;
        this.createdUserCounter = createdUserCounter;
    }

    public Mono<User> create(User user) {

        return userRepository
                .save(user)
                .doOnSuccess(savedUser -> createdUserCounter.increment());
    }
}
