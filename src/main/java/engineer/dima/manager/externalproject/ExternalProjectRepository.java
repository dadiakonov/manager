package engineer.dima.manager.externalproject;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ExternalProjectRepository extends ReactiveCrudRepository<ExternalProject, UUID> {
    Flux<ExternalProject> findByUserId(UUID userId);
}
