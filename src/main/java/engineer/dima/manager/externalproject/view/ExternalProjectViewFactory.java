package engineer.dima.manager.externalproject.view;

import engineer.dima.manager.externalproject.ExternalProject;

public class ExternalProjectViewFactory {
    public static ExternalProjectView create(ExternalProject externalProject) {
        return new ExternalProjectView(externalProject.externalProjectId().toString(),
                externalProject.userId().toString(), externalProject.name());
    }
}
