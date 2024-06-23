package engineer.dima.manager.externalproject;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class ExternalProjectReader {
    private final ExternalProjectRepository externalProjectRepository;

    public ExternalProjectReader(ExternalProjectRepository externalProjectRepository) {
        this.externalProjectRepository = externalProjectRepository;
    }

    public Flux<ExternalProject> findAllByUserId(UUID userId) {

        return externalProjectRepository.findByUserId(userId);
    }
}
