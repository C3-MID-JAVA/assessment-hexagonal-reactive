package ec.com.sofka.mapper;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionEntity;

public class TransactionMapperEntity {
    public static Transaction fromEntity(TransactionEntity transactionEntity){
        return new Transaction(
                transactionEntity.getId(),
                transactionEntity.getAmount(),
                transactionEntity.getFee(),
                transactionEntity.getNetAmount(),
                transactionEntity.getType(),
                transactionEntity.getTimestamp(),
                transactionEntity.getAccountId()
        );
    }

    public static TransactionEntity toEntity(Transaction transaction){
        return new TransactionEntity(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getFee(),
                transaction.getNetAmount(),
                transaction.getType(),
                transaction.getTimestamp(),
                transaction.getAccountId()
        );
    }
}
