package ec.com.sofka.mapper;

import ec.com.sofka.Transaction;
import ec.com.sofka.document.TransactionEntity;

public class TransactionRepoMapper {

    public static Transaction toDomain(TransactionEntity entity) {
        if (entity == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setId(entity.getId());
        transaction.setDescription(entity.getDescription());
        transaction.setAmount(entity.getAmount());
        transaction.setTransactionType(entity.getTransactionType());
        transaction.setDate(entity.getDate());
        transaction.setAccountId(entity.getAccountId());
        return transaction;
    }

    public static TransactionEntity toEntity(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setDescription(transaction.getDescription());
        entity.setAmount(transaction.getAmount());
        entity.setTransactionType(transaction.getTransactionType());
        entity.setDate(transaction.getDate());
        entity.setAccountId(transaction.getAccountId());
        return entity;
    }
}
