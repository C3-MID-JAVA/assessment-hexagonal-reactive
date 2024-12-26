package ec.com.sofka.transactions;

import ec.com.sofka.Account;
import ec.com.sofka.ConflictException;
import ec.com.sofka.Transaction;
import ec.com.sofka.enums.OperationType;
import ec.com.sofka.enums.TransactionType;
import ec.com.sofka.gateway.IAccountRepository;
import ec.com.sofka.gateway.ITransactionRepository;
import ec.com.sofka.gateway.TransaccionStrategyFactory;
import ec.com.sofka.strategy.TransaccionStrategy;
import ec.com.sofka.strategy.TransaccionStrategyContext;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class TransactionUseCase {

    private final ITransactionRepository transactionRepository;
    private final IAccountRepository accountRepository;
    private final TransaccionStrategyFactory strategyFactory;
    private final Predicate<BigDecimal> isSaldoInsuficiente = saldo -> saldo.compareTo(BigDecimal.ZERO) < 0;

    public TransactionUseCase(ITransactionRepository transactionRepository, IAccountRepository accountRepository, TransaccionStrategyFactory strategyFactory) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.strategyFactory = strategyFactory;
    }

    public Mono<Transaction> procesarTransaccion(Transaction transaction, OperationType tipoOperacion) {
        return getAccountAndStrategy(transaction, tipoOperacion)
                .flatMap(context -> {
                    Account cuenta = context.getCuenta();
                    TransaccionStrategy strategy = context.getStrategy();
                    BigDecimal costoTransaccion = strategy.getAmount();
                    BigDecimal saldoFinal = calculateFinalBalance(cuenta.getBalance(), transaction.getAmount(), costoTransaccion, tipoOperacion);

                    if (isSaldoInsuficiente.test(saldoFinal)) {
                        return Mono.error(new ConflictException("Insufficient balance for transaction."));
                    }

                    cuenta.setBalance(saldoFinal);
                    return saveAccountAndTransaction(cuenta, transaction.getAmount(), costoTransaccion, transaction.getType());
                });
    }

    private Mono<TransaccionStrategyContext> getAccountAndStrategy(Transaction transaction, OperationType tipoOperacion) {
        return accountRepository.findByAccountNumber(transaction.getAccountId())
                .switchIfEmpty(Mono.error(new NoSuchElementException("Cuenta no encontrada con el numero de Cuenta: " + transaction.getAccountId())))
                .flatMap(cuenta -> {
                    TransaccionStrategy strategy = strategyFactory.getStrategy(transaction.getType(), tipoOperacion);
                    strategy.validate(cuenta, transaction.getAmount());
                    return Mono.just(new TransaccionStrategyContext(cuenta, strategy, transaction.getAmount()));
                });
    }

    private BigDecimal calculateFinalBalance(BigDecimal saldoActual, BigDecimal monto, BigDecimal costo, OperationType tipo) {
        return tipo == OperationType.DEPOSIT
                ? saldoActual.add(monto).subtract(costo)
                : saldoActual.subtract(monto.add(costo));
    }

    private Mono<Transaction> saveAccountAndTransaction(Account cuenta, BigDecimal monto, BigDecimal costo, TransactionType tipoTransaccion) {
        return accountRepository.save(cuenta)
                .flatMap(cuentaGuardada -> {
                    Transaction transaccion = new Transaction(null,monto, costo, LocalDateTime.now(), tipoTransaccion, cuentaGuardada.getId());
                    return transactionRepository.save(transaccion);
                });
    }

}
