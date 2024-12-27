package ec.com.sofka.rules.impl;

import ec.com.sofka.Transaction;
import ec.com.sofka.rules.BalanceCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BalanceCalculatorImpl implements BalanceCalculator {

    @Override
    public BigDecimal calculate(Transaction transaction, BigDecimal currentBalance) {
        return transaction.getTransactionType().getDiscount() ?
                currentBalance.subtract(transaction.getValue().add(transaction.getTransactionType().getValue())) :
                currentBalance.add(transaction.getValue().add(transaction.getTransactionType().getValue()));
    }

}