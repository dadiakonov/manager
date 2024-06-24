package engineer.dima.manager.externalproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExternalProjectReaderTest {
    @Mock
    private ExternalProjectRepository externalProjectRepository;
    private ExternalProjectReader externalProjectReader;

    @BeforeEach
    public void setUp() {
        externalProjectReader = new ExternalProjectReader(externalProjectRepository);
    }

    @Test
    public void findAllByUserIdReturnsProjectsForGivenUserId() {
        UUID userId = UUID.randomUUID();
        ExternalProject externalProject = mock(ExternalProject.class);
        when(externalProjectRepository.findByUserId(userId))
                .thenReturn(Flux.just(externalProject));

        StepVerifier.create(externalProjectReader.findAllByUserId(userId))
                .expectNext(externalProject)
                .verifyComplete();
    }

    @Test
    public void findAllByUserIdReturnsEmptyWhenNoProjectsForGivenUserId() {
        UUID userId = UUID.randomUUID();
        when(externalProjectRepository.findByUserId(userId)).thenReturn(Flux.empty());

        StepVerifier.create(externalProjectReader.findAllByUserId(userId))
                .verifyComplete();
    }
}
