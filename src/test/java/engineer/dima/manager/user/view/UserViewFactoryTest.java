package engineer.dima.manager.user.view;

import engineer.dima.manager.user.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserViewFactoryTest {

    @Test
    public void createReturnsUserViewWithCorrectValues() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String name = "Test User";

        User user = new User(userId, email, "password", name);

        UserView userView = UserViewFactory.create(user);

        assertEquals(userId.toString(), userView.userId());
        assertEquals(email, userView.email());
        assertEquals(name, userView.name());
    }
}
