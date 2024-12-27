package co.com.sofka.bankingaccount.bankingaccount.domain.factory;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.impl.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class TransactionFactory {
    private final Map<String, BiFunction<BigDecimal, String, TransactionEntity>> transactionStrategy = new HashMap<>();

    // Predicate para validar si el monto es válido
    private final Predicate<BigDecimal> isAmountValid = amount -> amount.compareTo(BigDecimal.ZERO) > 0;

    // Function para calcular costos adicionales dependiendo del tipo de transacción
    private final Function<String, BigDecimal> calculateCost = type -> {
        return switch (type.toUpperCase()) {
            case "ATM_DEPOSIT" -> BigDecimal.valueOf(2); //Costo de depósito en cajero
            case "ATM_WITHDRAWAL" -> BigDecimal.valueOf(1); // Costo de retiro en cajero
            case "CARD_WEB_BUY" -> BigDecimal.valueOf(5); // Costo de compra en web
            case "DEPOSIT_FROM_ANOTHER_ACCOUNT" -> BigDecimal.valueOf(1.5); // Costo por depósito de otra cuenta
            default -> BigDecimal.ZERO; // Sin costo para otras transacciones
        };
    };

    public TransactionFactory() {
        // Strategy Registry
        transactionStrategy.put("ATM_DEPOSIT", AtmDepositEntity::new);
        transactionStrategy.put("ATM_WITHDRAWAL", AtmWithdrawalEntity::new);
        transactionStrategy.put("BRANCH_DEPOSIT", BranchDepositEntity::new);
        transactionStrategy.put("CARD_PHYSICAL_BUY", CardPhysicalBuyEntity::new);
        transactionStrategy.put("CARD_WEB_BUY", CardWebBuyEntity::new);
        transactionStrategy.put("DEPOSIT_FROM_ANOTHER_ACCOUNT", DepositFromAnotherAccountEntity::new);
    }

    public TransactionEntity createTransaction(String type, BigDecimal amount, String accountId){
        // Validar si el monto es válido usando Predicate
        if (!isAmountValid.test(amount)) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        BiFunction<BigDecimal, String, TransactionEntity> strategy = transactionStrategy.get(type.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Transaction Type not valid: " + type);
        }

        // Crear la transacción
        TransactionEntity transaction = strategy.apply(amount, accountId);

        // Calcular y asignar el costo usando Function
        BigDecimal cost = calculateCost.apply(type);
        transaction.setCost(cost);

        return transaction;
    }
}
