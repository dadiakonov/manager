package engineer.dima.manager.externalproject.view;

import engineer.dima.manager.externalproject.ExternalProject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExternalProjectViewFactoryTest {

    @Test
    public void createReturnsExternalProjectViewWithCorrectValues() {
        UUID externalProjectId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String name = "Test Project";

        ExternalProject externalProject = new ExternalProject(externalProjectId, userId, name);

        ExternalProjectView externalProjectView = ExternalProjectViewFactory.create(externalProject);

        assertEquals(externalProjectId.toString(), externalProjectView.externalProjectId());
        assertEquals(userId.toString(), externalProjectView.userId());
        assertEquals(name, externalProjectView.name());
    }
}
