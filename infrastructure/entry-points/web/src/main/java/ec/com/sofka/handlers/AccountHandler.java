package ec.com.sofka.handlers;

import ec.com.sofka.CreateAccountUseCase;
import ec.com.sofka.GetAllAccountsUseCase;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.mapper.AccountDTOMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {
    private final GetAllAccountsUseCase getAllAccountsUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    public AccountHandler(GetAllAccountsUseCase getAllAccountsUseCase, CreateAccountUseCase createAccountUseCase) {
        this.getAllAccountsUseCase = getAllAccountsUseCase;
        this.createAccountUseCase = createAccountUseCase;
    }

    public Flux<AccountResponseDTO> getAllAccounts() {

        return getAllAccountsUseCase.apply().map(AccountDTOMapper::fromEntity);

    }

    public Mono<AccountResponseDTO> createAccount(AccountRequestDTO accountRequestDTO) {
        return createAccountUseCase.apply(AccountDTOMapper.toEntity(accountRequestDTO))
                .map(AccountDTOMapper::fromEntity);
    }

}
