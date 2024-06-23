package engineer.dima.manager.user;

import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("users")
public record User(
        @Id
        UUID userId,

        @Email
        String email,

        String password,

        String name
) {
}
