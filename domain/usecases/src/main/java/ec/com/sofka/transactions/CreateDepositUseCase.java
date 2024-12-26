package ec.com.sofka.transactions;

import ec.com.sofka.Transaction;
import ec.com.sofka.enums.OperationType;
import ec.com.sofka.transactions.transactionprocess.ProcessTransactionUseCase;
import reactor.core.publisher.Mono;

public class CreateDepositUseCase {

    private final TransactionUseCase transactionUseCase;

    public CreateDepositUseCase(TransactionUseCase transactionUseCase) {
        this.transactionUseCase = transactionUseCase;
    }

    public Mono<Transaction> apply(Transaction transaction) {
        return transactionUseCase.procesarTransaccion(transaction, OperationType.DEPOSIT);
    }

}
