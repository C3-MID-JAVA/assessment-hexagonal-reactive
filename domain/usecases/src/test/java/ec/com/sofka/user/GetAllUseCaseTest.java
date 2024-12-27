package ec.com.sofka.user;

import ec.com.sofka.User;
import ec.com.sofka.gateway.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllUseCase getAllUseCase;

    @Test
    void shouldGetAllUsersSuccessfully(){
        List<User> users = List.of(
                new User("675e0e1259d6de4eda5b29b6", "Jhon Doe", "12345678"),
                new User("675e0e1259d6de4eda5b29b", "No name", "12345679")
        );

        when(userRepository.getAll()).thenReturn(Flux.fromIterable(users));

        StepVerifier.create(getAllUseCase.apply())
                .expectNext(users.get(0))
                .expectNext(users.get(1))
                .verifyComplete();

        verify(userRepository, times(1)).getAll();
    }
}