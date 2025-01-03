package ec.com.sofka.handler.account;

import ec.com.sofka.cases.account.GetAllAccountsUseCase;
import ec.com.sofka.dto.response.AccountResponseDTO;
import ec.com.sofka.mapper.AccountRequestMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetAllAccountsHandler {

    private final GetAllAccountsUseCase getAllAccountsUseCase;

    public GetAllAccountsHandler(GetAllAccountsUseCase getAllAccountsUseCase) {
        this.getAllAccountsUseCase = getAllAccountsUseCase;
    }

    public Flux<AccountResponseDTO> handle() {
        return getAllAccountsUseCase.getAll()
                .map(AccountRequestMapper::mapToDTO);
    }
}
