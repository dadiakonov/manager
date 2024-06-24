package engineer.dima.manager.user;

import engineer.dima.manager.security.SecurityConfig;
import engineer.dima.manager.security.TestAuthenticationConfig;
import engineer.dima.manager.user.view.UserView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = UserController.class)
@Import({SecurityConfig.class, TestAuthenticationConfig.class})
public class UserControllerTest {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String NAME = "John Doe";

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserCreator userCreator;

    @MockBean
    private UserUpdater userUpdater;

    @MockBean
    private UserRemover userRemover;

    @MockBean
    private UserReader userReader;

    @MockBean
    private UserFactory userFactory;

    @BeforeEach
    public void setUp() {
        reset(userCreator, userUpdater, userRemover, userReader, userFactory);
    }

    @Test
    public void testCreateUserValidRequest() {
        UserRequest userRequest = new UserRequest(EMAIL, PASSWORD, NAME);
        User user = new User(null, EMAIL, ENCODED_PASSWORD, NAME);
        User createdUser = new User(UUID.randomUUID(), EMAIL, ENCODED_PASSWORD, NAME);
        UserView userView = new UserView(createdUser.userId().toString(), EMAIL, NAME);

        when(userFactory.create(userRequest))
                .thenReturn(user);
        when(userCreator.create(user))
                .thenReturn(Mono.just(createdUser));

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserView.class)
                .isEqualTo(userView);
    }

    @Test
    public void testCreateUserWithMissingFieldsRequest() {
        UserRequest invalidUserRequest = new UserRequest("", "", "");

        String expectedResponse = """
        {
          "errors": [
            {
              "field": "email",
              "message": "The email field is required."
            },
            {
              "field": "password",
              "message": "The password field is required."
            }
          ]
        }
        """;


        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidUserRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    public void testCreateUserWithInvalidEmailRequest() {
        UserRequest invalidUserRequest = new UserRequest("invalid email", PASSWORD, NAME);

        String expectedResponse = """
        {
          "errors": [
            {
              "field": "email",
              "message": "The email field must be a valid email address."
            }
          ]
        }
        """;


        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidUserRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .json(expectedResponse);
    }
}
