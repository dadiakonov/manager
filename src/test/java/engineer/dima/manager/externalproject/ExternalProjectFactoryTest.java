package engineer.dima.manager.externalproject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ExternalProjectFactoryTest {

    @Test
    public void createReturnsExternalProjectWithCorrectValues() {
        UUID userId = UUID.randomUUID();
        String name = "Test Project";
        ExternalProjectRequest externalProjectRequest = new ExternalProjectRequest(name);

        ExternalProject externalProject = ExternalProjectFactory.create(userId, externalProjectRequest);

        assertEquals(userId, externalProject.userId());
        assertEquals(name, externalProject.name());
        assertNull(externalProject.externalProjectId());
    }
}
