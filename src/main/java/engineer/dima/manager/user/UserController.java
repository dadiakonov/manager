package engineer.dima.manager.user;

import engineer.dima.manager.user.view.UserView;
import engineer.dima.manager.user.view.UserViewFactory;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final static String USER_NOT_FOUND_MESSAGE = "User not found.";
    private final static String USER_ALREADY_EXISTS_MESSAGE = "User already exists.";

    private final UserCreator userCreator;
    private final UserUpdater userUpdater;
    private final UserRemover userRemover;
    private final UserReader userReader;
    private final UserFactory userFactory;

    public UserController(UserCreator userCreator,
                          UserUpdater userUpdater,
                          UserRemover userRemover,
                          UserReader userReader,
                          UserFactory userFactory) {
        this.userCreator = userCreator;
        this.userUpdater = userUpdater;
        this.userRemover = userRemover;
        this.userReader = userReader;
        this.userFactory = userFactory;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserView> createUser(@RequestBody @Valid UserRequest userRequest) {

        return userCreator.create(userFactory.create(userRequest))
                .onErrorMap(DuplicateKeyException.class, exception -> new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_ALREADY_EXISTS_MESSAGE))
                .map(UserViewFactory::create);
    }

    @GetMapping("/{userId}")
    public Mono<UserView> getUser(@PathVariable UUID userId, Authentication authentication) {

        return userReader.findByEmail((String) authentication.getPrincipal())
                .filter(user -> user.userId().equals(userId))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE)))
                .map(UserViewFactory::create);
    }

    @PutMapping("/{userId}")
    public Mono<UserView> updateUser(@PathVariable UUID userId, @RequestBody @Valid UserRequest userRequest, Authentication authentication) {

        return userReader.findByEmail((String) authentication.getPrincipal())
                .filter(user -> user.userId().equals(userId))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE)))
                .map(user -> userFactory.create(user.userId(), userRequest))
                .flatMap(userUpdater::update)
                .map(UserViewFactory::create);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable UUID userId, Authentication authentication) {

        return userReader.findByEmail((String) authentication.getPrincipal())
                .filter(user -> user.userId().equals(userId))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE)))
                .flatMap(userRemover::remove);
    }
}
