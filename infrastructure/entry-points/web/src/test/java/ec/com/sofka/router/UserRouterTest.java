package ec.com.sofka.router;

import ec.com.sofka.data.UserRequestDTO;
import ec.com.sofka.data.UserResponseDTO;
import ec.com.sofka.exception.RequestValidationException;
import ec.com.sofka.exception.RequestValidator;
import ec.com.sofka.handler.user.CreateUserHandler;
import ec.com.sofka.handler.user.GetAllUsersHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.Matchers.any;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class UserRouterTest {
    @Mock
    private RequestValidator requestValidator;

    @Mock
    private CreateUserHandler createUserHandler;

    @Mock
    private GetAllUsersHandler getAllUsersHandler;

    @InjectMocks
    private UserRouter userRouter;

    private WebTestClient webTestClient;

    private UserRequestDTO userRequest;
    private UserResponseDTO userResponse;

    @BeforeEach
    void setUp() {

        webTestClient = WebTestClient.bindToRouterFunction(userRouter.usersRouters()).build();

        userRequest = new UserRequestDTO("Juan", "Zambrano", "1000000000", "user@gmail.com", "User123.", "ACTIVE");
        userResponse = new UserResponseDTO("Juan", "Zambrano", "1000000000", "user@gmail.com", "ACTIVE");

    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(requestValidator.validate(any(UserRequestDTO.class))).thenReturn(Mono.just(userRequest));
        when(createUserHandler.save(userRequest)).thenReturn(Mono.just(userResponse));

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.firstName").isEqualTo(userRequest.getFirstName())
                .jsonPath("$.email").isEqualTo(userRequest.getEmail());
    }

    @Test
    void shouldReturnValidationErrors() {
        UserRequestDTO invalidRequest = new UserRequestDTO(null, "Zambrano", "1000000000", "invalid-email", "pass", "ACTIVE");

        when(requestValidator.validate(any(UserRequestDTO.class)))
                .thenReturn(Mono.error(new RequestValidationException(List.of("firstName cannot be null", "Invalid email"))));

        webTestClient.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.errors").isArray()
                .jsonPath("$.errors[0]").isEqualTo("firstName cannot be null")
                .jsonPath("$.errors[1]").isEqualTo("Invalid email");
    }

    @Test
    void shouldGetAllUsersSuccessfully() {
        UserResponseDTO user1 = new UserResponseDTO("Juan", "Zambrano", "1234567890", "user@gmail.com", "ACTIVE");
        UserResponseDTO user2 = new UserResponseDTO("Maria", "Perez", "0987654321", "maria@gmail.com", "INACTIVE");

        when(getAllUsersHandler.getAllUsers()).thenReturn(Flux.just(user1, user2));

        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].ci").isEqualTo(user1.getCi())
                .jsonPath("$[1].firstName").isEqualTo(user2.getFirstName());
    }

}