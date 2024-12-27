package ec.com.sofka.user;

import ec.com.sofka.User;
import ec.com.sofka.exception.ConflictException;
import ec.com.sofka.gateway.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void shouldRegisterUserSuccessfully() {
        User user = new User("John Doe", "123456789");
        User expectedUser = new User("675e0e1259d6de4eda5b29b7", "John Doe", "123456789");

        when(userRepository.findByDocumentId(user.getDocumentId())).thenReturn(Mono.empty());
        when(userRepository.create(user)).thenReturn(Mono.just(expectedUser));

        StepVerifier.create(createUserUseCase.apply(user))
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(expectedUser.getId(), response.getId());
                    assertEquals(expectedUser.getName(), response.getName());
                    assertEquals(expectedUser.getDocumentId(), response.getDocumentId());
                })
                .verifyComplete();

        verify(userRepository, times(1)).findByDocumentId(user.getDocumentId());
        verify(userRepository, times(1)).create(user);
    }

    @Test
    void whenDocumentIdAlreadyExists_shouldThrowException() {
        User user = new User("John Doe", "123456789");
        User existingUser = new User("675e0e1259d6de4eda5b29b7", "John Doe", "123456789");

        when(userRepository.findByDocumentId(user.getDocumentId())).thenReturn(Mono.just(existingUser));

        StepVerifier.create(createUserUseCase.apply(user))
                .expectErrorMatches(ex -> ex instanceof ConflictException &&
                        ex.getMessage().equals("Document ID already exists."))
                .verify();

        verify(userRepository, times(1)).findByDocumentId(user.getDocumentId());
        verify(userRepository, never()).create(any(User.class));
    }
}