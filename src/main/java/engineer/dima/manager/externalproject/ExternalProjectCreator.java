package engineer.dima.manager.externalproject;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExternalProjectCreator {
    private final ExternalProjectRepository externalProjectRepository;

    public ExternalProjectCreator(ExternalProjectRepository externalProjectRepository) {
        this.externalProjectRepository = externalProjectRepository;
    }

    public Mono<ExternalProject> create(ExternalProject externalProject) {

        return externalProjectRepository.save(externalProject);
    }
}
