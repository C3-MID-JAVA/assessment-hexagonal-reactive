package ec.com.sofka.transactions.transactionprocess;

import ec.com.sofka.Account;
import ec.com.sofka.enums.OperationType;
import ec.com.sofka.enums.TransactionType;
import ec.com.sofka.gateway.TransaccionStrategyFactory;
import ec.com.sofka.strategy.TransaccionStrategy;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class GetTransactionStrategyUseCase {

    private final TransaccionStrategyFactory factory;

    public GetTransactionStrategyUseCase(TransaccionStrategyFactory factory) {
        this.factory = factory;
    }

    public Mono<TransaccionStrategy> apply(Account account, TransactionType type, OperationType operationType, BigDecimal amount) {
        TransaccionStrategy strategy = factory.getStrategy(type, operationType);
        strategy.validate(account, amount); // Simulamos la validación
        return Mono.just(strategy);
    }
}
