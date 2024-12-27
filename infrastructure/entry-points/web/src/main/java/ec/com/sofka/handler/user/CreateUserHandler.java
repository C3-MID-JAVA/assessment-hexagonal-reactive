package ec.com.sofka.handler.user;

import ec.com.sofka.cases.user.CreateUserUseCase;
import ec.com.sofka.data.UserRequestDTO;
import ec.com.sofka.data.UserResponseDTO;
import ec.com.sofka.mapper.UserModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateUserHandler {
    private final CreateUserUseCase createUserUseCase;

    public CreateUserHandler(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public Mono<UserResponseDTO> save(UserRequestDTO user) {
        return createUserUseCase.apply(UserModelMapper.mapToModel(user))
                .map(UserModelMapper::mapToDTO);
    }

}