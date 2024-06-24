package engineer.dima.manager.externalproject;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExternalProjectCreator {
    private final ExternalProjectRepository externalProjectRepository;
    private final CreatedExternalProjectCounter createdExternalProjectCounter;

    public ExternalProjectCreator(ExternalProjectRepository externalProjectRepository,
                                  CreatedExternalProjectCounter createdExternalProjectCounter) {
        this.externalProjectRepository = externalProjectRepository;
        this.createdExternalProjectCounter = createdExternalProjectCounter;
    }

    public Mono<ExternalProject> create(ExternalProject externalProject) {

        return externalProjectRepository
                .save(externalProject)
                .doOnSuccess(savedExternalProject -> createdExternalProjectCounter.increment());
    }
}
