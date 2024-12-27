package ec.com.sofka.handlers.account;

import ec.com.sofka.usecases.account.GetAllAccountsUseCase;
import ec.com.sofka.data.dto.accountDTO.AccountResponseDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GetAllAccountsHandler {
    private final GetAllAccountsUseCase getAllAccounts;

    public GetAllAccountsHandler(GetAllAccountsUseCase getAllAccounts) {
        this.getAllAccounts = getAllAccounts;
    }

    public Flux<AccountResponseDTO> getAccounts() {
        return getAllAccounts.apply()
                .map(account -> new AccountResponseDTO(
                        account.getId(),
                        account.getBalance(),
                        account.getAccountNumber(),
                        account.getOwner()    // Extrae el número de cuenta
                ))
                .switchIfEmpty(Mono.empty());
    }
}
