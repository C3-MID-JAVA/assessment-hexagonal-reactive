package ec.com.sofka.handler.account;

import ec.com.sofka.Account;
import ec.com.sofka.cases.account.CreateAccountUseCase;
import ec.com.sofka.cases.user.FindUserByIdUseCase;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.mapper.AccountModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateAccountHandler {
    private final CreateAccountUseCase createAccountUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public CreateAccountHandler(CreateAccountUseCase createAccountUseCase, FindUserByIdUseCase findUserByIdUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    public Mono<AccountResponseDTO> save(AccountRequestDTO accountRequestDTO) {
        return findUserByIdUseCase.apply(accountRequestDTO.getUserId())
                .flatMap(user -> {
                    Account account = AccountModelMapper.mapToModel(accountRequestDTO);
                    account.setUser(user);

                    return createAccountUseCase.apply(account);
                })
                .map(AccountModelMapper::mapToDTO);
    }

}