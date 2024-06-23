package engineer.dima.manager.user;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserRemover {
    private final UserRepository userRepository;

    public UserRemover(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<Void> remove(User user) {

        return userRepository.delete(user);
    }
}
