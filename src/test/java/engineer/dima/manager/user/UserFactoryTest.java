package engineer.dima.manager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserFactoryTest {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "Test User";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    @Mock
    private PasswordEncoder passwordEncoder;
    private UserFactory userFactory;

    @BeforeEach
    public void setUp() {
        userFactory = new UserFactory(passwordEncoder);
    }

    @Test
    public void createReturnsUserWithCorrectValues() {
        UUID userId = UUID.randomUUID();

        UserRequest userRequest = new UserRequest(EMAIL, PASSWORD, NAME);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        User user = userFactory.create(userId, userRequest);

        assertEquals(userId, user.userId());
        assertEquals(EMAIL, user.email());
        assertEquals(ENCODED_PASSWORD, user.password());
        assertEquals(NAME, user.name());
    }

    @Test
    public void createReturnsUserWithNullId() {
        UserRequest userRequest = new UserRequest(EMAIL, PASSWORD, NAME);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        User user = userFactory.create(userRequest);

        assertNull(user.userId());
        assertEquals(EMAIL, user.email());
        assertEquals(ENCODED_PASSWORD, user.password());
        assertEquals(NAME, user.name());
    }
}
