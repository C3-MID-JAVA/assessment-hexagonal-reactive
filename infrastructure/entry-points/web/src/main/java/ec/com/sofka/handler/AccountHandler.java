package ec.com.sofka.handler;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountInDTO;
import ec.com.sofka.data.AccountOutDTO;
import ec.com.sofka.exception.ResourceNotFoundException;
import ec.com.sofka.mapper.AccountMapper;
import ec.com.sofka.usecase.account.FindAccountUseCase;
import ec.com.sofka.usecase.account.SaveAccountUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Validated
public class AccountHandler {

    private final SaveAccountUseCase saveAccountUseCase;
    private final FindAccountUseCase findAccountUseCase;

    public AccountHandler(SaveAccountUseCase saveAccountUseCase,
                          FindAccountUseCase findAccountUseCase) {
        this.saveAccountUseCase = saveAccountUseCase;
        this.findAccountUseCase = findAccountUseCase;
    }

    public Mono<AccountOutDTO> saveAccount(@Valid AccountInDTO accountInDTO) {
        if (accountInDTO == null) {
            return Mono.error(new IllegalArgumentException("AccountInDTO cannot be null"));
        }
        Account account = AccountMapper.toEntity(accountInDTO);
        return saveAccountUseCase.save(account)
                .map(AccountMapper::toDTO);
    }

    public Mono<AccountOutDTO> findAccountById(String id) {
        if (id == null || id.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Account ID cannot be null or empty"));
        }
        return findAccountUseCase.findById(id)
                .map(AccountMapper::toDTO)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Account not found with id: " + id)));
    }

    public Flux<AccountOutDTO> findAllAccounts() {
        return findAccountUseCase.findAll()
                .map(AccountMapper::toDTO)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No accounts found")));
    }
}
