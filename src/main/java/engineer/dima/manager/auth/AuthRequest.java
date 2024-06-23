package engineer.dima.manager.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "The email field is required.")
        @Size(max = 255, message = "Max length of the email field is 255 characters.")
        String email,

        @NotBlank(message = "The password field is required.")
        String password
) {
}
