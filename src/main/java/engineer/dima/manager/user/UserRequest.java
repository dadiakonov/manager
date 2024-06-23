package engineer.dima.manager.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Email(message = "The email field must be a valid email address.")
        @NotBlank(message = "The email field is required.")
        @Size(max = 255, message = "Max length of the email field is 255 characters.")
        String email,

        @NotBlank(message = "The password field is required.")
        String password,

        @Size(max = 255, message = "Max length of the name field is 255 characters.")
        String name
) {
}
