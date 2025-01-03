package ec.com.sofka.handler.transaction;

import ec.com.sofka.dto.response.TransactionResponseDTO;
import ec.com.sofka.facade.FinanceFacade;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GetAllTransactionsHandler {

    private final FinanceFacade financeFacade;

    public GetAllTransactionsHandler(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    public Mono<ServerResponse> handle() {
        return financeFacade.getAllTransactions()
                .collectList()
                .flatMap(transactions -> {
                    if (transactions.isEmpty()) {
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().bodyValue(transactions);
                });
    }
}

