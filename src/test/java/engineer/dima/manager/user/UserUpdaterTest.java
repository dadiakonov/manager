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
public class UserUpdaterTest {
    @Mock
    private UserRepository userRepository;
    private UserUpdater userUpdater;

    @BeforeEach
    public void setUp() {
        userUpdater = new UserUpdater(userRepository);
    }

    @Test
    public void updateSavesUser() {
        User user = mock(User.class);
        when(userRepository.save(user))
                .thenReturn(Mono.just(user));

        StepVerifier.create(userUpdater.update(user))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateDoesNotSaveWhenSaveFails() {
        User user = mock(User.class);
        when(userRepository.save(user))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(userUpdater.update(user))
                .expectError()
                .verify();

        verify(userRepository, times(1)).save(user);
    }
}
