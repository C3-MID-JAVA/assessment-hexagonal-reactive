package ec.com.sofka.handler.account;

import ec.com.sofka.accounts.*;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.mapper.AccountDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Component
public class AccountHandler {
    private final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final GetCheckBalanceUseCase getCheckBalanceUseCase;
    private final GetAccountsUseCase getAccountsUseCase;

    public AccountHandler(GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase,CreateAccountUseCase createAccountUseCase,
                          GetAccountsUseCase getAccountsUseCase,GetAccountByIdUseCase getAccountByIdUseCase, GetCheckBalanceUseCase getCheckBalanceUseCase) {
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;
        this.createAccountUseCase = createAccountUseCase;
        this.getAccountsUseCase = getAccountsUseCase;
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.getCheckBalanceUseCase = getCheckBalanceUseCase;
    }

    public Mono<AccountResponseDTO> getAccountByAccountNumber(String accountNumber) {
        return getAccountByAccountNumberUseCase.apply(accountNumber).map(AccountDTOMapper::accountToAccountResponseDTO);
    }

    public Mono<AccountResponseDTO> createAccount(AccountRequestDTO accountRequestDTO ) {
        return createAccountUseCase.apply(AccountDTOMapper.accountRequestToAccount(accountRequestDTO)).
                map(AccountDTOMapper::accountToAccountResponseDTO);
    }

    public Mono<AccountResponseDTO> getAccountByAccountId(String accountId) {
        return getAccountByIdUseCase.apply(accountId)
                .map(AccountDTOMapper::accountToAccountResponseDTO);
    }

    public Mono<BigDecimal> getCheckBalance(String accountId) {
        return getCheckBalanceUseCase.apply(accountId);
    }

    public Flux<AccountResponseDTO> getAccounts() {
        return getAccountsUseCase.apply().map(AccountDTOMapper::accountToAccountResponseDTO);
    }

}
