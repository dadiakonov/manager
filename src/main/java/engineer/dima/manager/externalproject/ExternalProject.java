package engineer.dima.manager.externalproject;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("external_projects")
public record ExternalProject(
        @Id
        UUID externalProjectId,

        UUID userId,

        String name
) {
}
