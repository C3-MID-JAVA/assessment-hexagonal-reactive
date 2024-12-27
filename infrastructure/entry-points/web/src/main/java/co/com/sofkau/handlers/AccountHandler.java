package co.com.sofkau.handlers;

import co.com.sofkau.CreateAccountUseCase;
import co.com.sofkau.GetAllAccountsUseCase;
import co.com.sofkau.data.AccountDTO;
import co.com.sofkau.mapper.AccountDTOMapper;
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

    public Flux<AccountDTO> getAllAccounts() {

        return getAllAccountsUseCase.apply().map(AccountDTOMapper::toAccountDTO);

    }

    public Mono<AccountDTO> createAccount(AccountDTO accountDTO) {
        return createAccountUseCase.apply(AccountDTOMapper.toAccount(accountDTO)).map(AccountDTOMapper::toAccountDTO);
    }
}
