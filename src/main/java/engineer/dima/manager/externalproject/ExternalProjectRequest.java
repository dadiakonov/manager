package engineer.dima.manager.externalproject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExternalProjectRequest(
        @NotBlank(message = "The name field is required.")
        @Size(max = 255, message = "Max length of the name field is 255 characters.")
        String name
) {
}
