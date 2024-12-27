package ec.com.sofka.transactions;

import ec.com.sofka.Transaction;
import ec.com.sofka.enums.OperationType;
import ec.com.sofka.transactions.transactionprocess.ProcessTransactionUseCase;
import reactor.core.publisher.Mono;

public class CreateWithDrawalUseCase {


    private final TransactionUseCase transactionUseCase;

    public CreateWithDrawalUseCase(TransactionUseCase transactionUseCase) {
        this.transactionUseCase = transactionUseCase;
    }

    public Mono<Transaction> apply(Transaction transaction) {
        return transactionUseCase.procesarTransaccion(transaction, OperationType.WITHDRAWAL);
    }

}
