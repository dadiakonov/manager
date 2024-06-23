package engineer.dima.manager.externalproject;

import java.util.UUID;

public class ExternalProjectFactory {
    public static ExternalProject create(UUID userId, ExternalProjectRequest externalProjectRequest) {
        return new ExternalProject(null, userId, externalProjectRequest.name());
    }
}
