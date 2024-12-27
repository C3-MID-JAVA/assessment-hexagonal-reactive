package ec.com.sofka;

import ec.com.sofka.data.RequestTransactionDTO;
import ec.com.sofka.data.ResponseTransactionDTO;
import ec.com.sofka.transactionsusescases.CreateTransactionsUseCase;
import ec.com.sofka.transactionsusescases.GetAllTransactionsUseCase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {


    private final GetAllTransactionsUseCase getAllTransactionsUseCase;
    private final CreateTransactionsUseCase createTransactionUseCase;

    public TransactionHandler(GetAllTransactionsUseCase getAllTransactionsUseCase,
                              CreateTransactionsUseCase createTransactionUseCase) {
        this.getAllTransactionsUseCase = getAllTransactionsUseCase;
        this.createTransactionUseCase = createTransactionUseCase;
    }

    // Este método devuelve solo el DTO, no maneja la respuesta HTTP
    public Flux<ResponseTransactionDTO> getAllTransactions() {
        return getAllTransactionsUseCase.getAllTransactions()
                .map(transaction -> new ResponseTransactionDTO(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getTransactionCost(),
                        transaction.getBankAccount(),
                        transaction.getTimestamp()
                ));
    }


    public Mono<ResponseTransactionDTO> createTransaction(RequestTransactionDTO dto) {
        // Mapeamos el DTO a un objeto de dominio
        Transaction transaction = new Transaction(
                "",
                dto.getType(),
                dto.getAmount(),
                dto.getTransactionCost(),
                new Account(dto.getBankAccountId()) // Crear cuenta solo con el ID
        );

        // Llamamos al caso de uso para crear la transacción
        return createTransactionUseCase.apply(transaction)
                .map(createdTransaction -> new ResponseTransactionDTO(
                        createdTransaction.getId(),
                        createdTransaction.getType(),
                        createdTransaction.getAmount(),
                        createdTransaction.getTransactionCost(),
                        createdTransaction.getBankAccount(),
                        createdTransaction.getTimestamp()
                ));
    }
}
