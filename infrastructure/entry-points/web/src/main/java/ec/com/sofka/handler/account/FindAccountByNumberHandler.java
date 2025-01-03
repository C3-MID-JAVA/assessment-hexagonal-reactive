package ec.com.sofka.handler.account;

import ec.com.sofka.cases.account.FindAccountByNumberUseCase;
import ec.com.sofka.mapper.AccountRequestMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FindAccountByNumberHandler {

    private final FindAccountByNumberUseCase findAccountByNumberUseCase;

    public FindAccountByNumberHandler(FindAccountByNumberUseCase findAccountByNumberUseCase) {
        this.findAccountByNumberUseCase = findAccountByNumberUseCase;
    }

    public Mono<ServerResponse> handle(String accountNumber) {
        return findAccountByNumberUseCase.find(accountNumber)
                .map(AccountRequestMapper::mapToDTO)
                .flatMap(responseDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseDTO))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}

