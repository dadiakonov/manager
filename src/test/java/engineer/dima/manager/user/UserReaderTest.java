package engineer.dima.manager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserReaderTest {
    private static final String EMAIL = "test@example.com";

    @Mock
    private UserRepository userRepository;
    private UserReader userReader;

    @BeforeEach
    public void setUp() {
        userReader = new UserReader(userRepository);
    }

    @Test
    public void findByEmailReturnsUserWhenUserExists() {
        User user = mock(User.class);
        when(userRepository.findByEmail(EMAIL))
                .thenReturn(Mono.just(user));

        StepVerifier.create(userReader.findByEmail(EMAIL))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    public void findByEmailReturnsEmptyWhenUserDoesNotExist() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Mono.empty());

        StepVerifier.create(userReader.findByEmail(EMAIL))
                .verifyComplete();
    }
}
