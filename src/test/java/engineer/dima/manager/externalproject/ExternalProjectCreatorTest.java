package engineer.dima.manager.externalproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExternalProjectCreatorTest {
    @Mock
    private ExternalProjectRepository externalProjectRepository;

    @Mock
    private CreatedExternalProjectCounter createdExternalProjectCounter;

    private ExternalProjectCreator externalProjectCreator;

    @BeforeEach
    public void setUp() {
        externalProjectCreator = new ExternalProjectCreator(externalProjectRepository, createdExternalProjectCounter);
    }

    @Test
    public void createSavesExternalProjectAndIncrementsCounter() {
        ExternalProject externalProject = mock(ExternalProject.class);
        when(externalProjectRepository.save(externalProject))
                .thenReturn(Mono.just(externalProject));

        StepVerifier.create(externalProjectCreator.create(externalProject))
                .expectNext(externalProject)
                .verifyComplete();

        verify(createdExternalProjectCounter, times(1)).increment();
    }

    @Test
    public void createDoesNotIncrementCounterWhenSaveFails() {
        ExternalProject externalProject = mock(ExternalProject.class);
        when(externalProjectRepository.save(externalProject))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(externalProjectCreator.create(externalProject))
                .expectError()
                .verify();

        verify(createdExternalProjectCounter, never()).increment();
    }
}
