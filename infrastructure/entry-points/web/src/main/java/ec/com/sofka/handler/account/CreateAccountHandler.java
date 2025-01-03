package ec.com.sofka.handler.account;

import ec.com.sofka.cases.account.CreateAccountUseCase;
import ec.com.sofka.dto.request.AccountRequestDTO;
import ec.com.sofka.dto.response.AccountResponseDTO;
import ec.com.sofka.mapper.AccountRequestMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateAccountHandler {

    private final CreateAccountUseCase createAccountUseCase;

    public CreateAccountHandler(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    public Mono<AccountResponseDTO> handle(AccountRequestDTO accountRequestDTO) {
        return createAccountUseCase.create(AccountRequestMapper.mapToModel(accountRequestDTO))
                .map(AccountRequestMapper::mapToDTO);
    }
}

