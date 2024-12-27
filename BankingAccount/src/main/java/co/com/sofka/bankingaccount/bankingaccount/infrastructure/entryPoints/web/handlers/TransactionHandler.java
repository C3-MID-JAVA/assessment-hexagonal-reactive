package co.com.sofka.bankingaccount.bankingaccount.infrastructure.entryPoints.web.handlers;

import co.com.sofka.bankingaccount.bankingaccount.application.dto.request.TransactionRequestDTO;
import co.com.sofka.bankingaccount.bankingaccount.domain.factory.TransactionFactory;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.ExecuteTransactionUseCase;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {
    private final TransactionFactory transactionFactory;
    private final ExecuteTransactionUseCase executeTransactionUseCase;

    public TransactionHandler(TransactionFactory transactionFactory, ExecuteTransactionUseCase executeTransactionUseCase) {
        this.transactionFactory = transactionFactory;
        this.executeTransactionUseCase = executeTransactionUseCase;
    }

    public Mono<ServerResponse> executeTransaction(ServerRequest request) {
        return request.bodyToMono(TransactionRequestDTO.class)
                .flatMap(transactionDTO -> {
                    // Usar la fábrica para crear la entidad de transacción
                    TransactionEntity transaction = transactionFactory.createTransaction(
                            transactionDTO.getType(),
                            transactionDTO.getAmount(),
                            transactionDTO.getId()
                    );

                    // Delegar al caso de uso
                    return executeTransactionUseCase.execute(transaction)
                            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response));
                })
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(e.getMessage()));
    }
}
