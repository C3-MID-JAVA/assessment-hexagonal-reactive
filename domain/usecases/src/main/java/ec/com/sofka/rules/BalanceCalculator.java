package ec.com.sofka.rules;

import ec.com.sofka.Transaction;

import java.math.BigDecimal;

@FunctionalInterface
public interface BalanceCalculator {
    BigDecimal calculate(Transaction transaction, BigDecimal currentBalance);
}