package ec.com.sofka.handler.transaction;

import ec.com.sofka.dto.request.TransactionRequestDTO;
import ec.com.sofka.dto.response.TransactionResponseDTO;
import ec.com.sofka.facade.FinanceFacade;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateTransactionHandler {

    private final FinanceFacade financeFacade;

    public CreateTransactionHandler(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    public Mono<TransactionResponseDTO> handle(TransactionRequestDTO transactionRequestDTO) {
        return financeFacade.processTransaction(transactionRequestDTO);
    }
}
