package ec.com.sofka.handlers.account;

import ec.com.sofka.usecases.account.GetAccountByIdUseCase;
import ec.com.sofka.data.dto.accountDTO.AccountResponseDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GetAccountByIdHandler {
    private final GetAccountByIdUseCase getAccountByIdUseCase;

    public GetAccountByIdHandler(GetAccountByIdUseCase getAccountByIdUseCase) {
        this.getAccountByIdUseCase = getAccountByIdUseCase;
    }

    public Mono<AccountResponseDTO> getAccountById(String id) {
        return getAccountByIdUseCase.apply(id)
                .map(account -> new AccountResponseDTO(
                        account.getId(),
                        account.getBalance(),
                        account.getAccountNumber(),
                        account.getOwner()          // Extrae el propietario
                           // Extrae el número de cuenta
                ))
                .switchIfEmpty(Mono.empty());
    }
}