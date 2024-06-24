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
public class UserCreatorTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private CreatedUserCounter createdUserCounter;

    private UserCreator userCreator;

    @BeforeEach
    public void setUp() {
        userCreator = new UserCreator(userRepository, createdUserCounter);
    }

    @Test
    public void createSavesUserAndIncrementsCounter() {
        User user = mock(User.class);
        when(userRepository.save(user))
                .thenReturn(Mono.just(user));

        StepVerifier.create(userCreator.create(user))
                .expectNext(user)
                .verifyComplete();

        verify(createdUserCounter, times(1)).increment();
    }

    @Test
    public void createDoesNotIncrementCounterWhenSaveFails() {
        User user = mock(User.class);
        when(userRepository.save(user))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(userCreator.create(user))
                .expectError()
                .verify();

        verify(createdUserCounter, never()).increment();
    }
}
