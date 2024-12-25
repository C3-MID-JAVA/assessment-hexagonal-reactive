package ec.com.sofka.handler.account;

import ec.com.sofka.Account;
import ec.com.sofka.accounts.CreateAccountUseCase;
import ec.com.sofka.accounts.GetAccountByAccountNumberUseCase;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.mapper.AccountDTOMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class AccountHandler {
    private final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    public AccountHandler(GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase,CreateAccountUseCase createAccountUseCase) {
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;
        this.createAccountUseCase = createAccountUseCase;
    }

    public Mono<AccountResponseDTO> getAccountByAccountNumber(String accountNumber) {
        return getAccountByAccountNumberUseCase.apply(accountNumber).map(AccountDTOMapper::accountToAccountResponseDTO);
    }

    public Mono<AccountResponseDTO> createAccount(AccountRequestDTO accountRequestDTO ) {
        return createAccountUseCase.apply(AccountDTOMapper.accountRequestToAccount(accountRequestDTO)).
                map(AccountDTOMapper::accountToAccountResponseDTO);
    }
}
