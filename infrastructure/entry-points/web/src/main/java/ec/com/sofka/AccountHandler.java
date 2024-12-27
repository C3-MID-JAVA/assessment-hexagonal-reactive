package ec.com.sofka;

import ec.com.sofka.accountusescases.CreateAccountUseCase;
import ec.com.sofka.accountusescases.GetAccountByIdUseCase;
import ec.com.sofka.data.RequestAccountDTO;
import ec.com.sofka.data.ResponseAccountDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {

    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    public AccountHandler(GetAccountByIdUseCase getAccountByIdUseCase,
                          CreateAccountUseCase createAccountUseCase) {
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.createAccountUseCase = createAccountUseCase;
    }

    public Mono<ResponseAccountDTO> getAccountById(String id) {
        return getAccountByIdUseCase.apply(id) // Devuelve un Mono<Account>
                .map(account -> new ResponseAccountDTO(
                        account.getId(),
                        account.getOwner(),
                        account.getBalance()
                ));
    }

    public Mono<ResponseAccountDTO> createAccount(RequestAccountDTO account) {
        Account newAccount = new Account(
                account.getId(),
                account.getInitialBalance(),
                account.getAccountOwner()
        );
        return createAccountUseCase.apply(newAccount) // Devuelve un Mono<Account>
                .map(accountSaved -> new ResponseAccountDTO(
                        accountSaved.getId(),
                        accountSaved.getOwner(),
                        accountSaved.getBalance()
                ));
    }


}
