package engineer.dima.manager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRemoverTest {
    @Mock
    private UserRepository userRepository;
    private UserRemover userRemover;

    @BeforeEach
    public void setUp() {
        userRemover = new UserRemover(userRepository);
    }

    @Test
    public void removeDeletesUser() {
        User user = mock(User.class);
        when(userRepository.delete(user))
                .thenReturn(Mono.empty());

        StepVerifier.create(userRemover.remove(user))
                .verifyComplete();

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void removeDoesNotDeleteWhenDeleteFails() {
        User user = mock(User.class);
        when(userRepository.delete(user))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(userRemover.remove(user))
                .expectError()
                .verify();

        verify(userRepository, times(1)).delete(user);
    }
}
