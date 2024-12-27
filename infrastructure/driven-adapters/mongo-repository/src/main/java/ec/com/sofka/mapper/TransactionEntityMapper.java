package ec.com.sofka.mapper;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityMapper {

    public static TransactionEntity mapToEntity(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionEntity(
                transaction.getId(),
                transaction.getAccountNumber(),
                transaction.getDetails(),
                transaction.getAccountNumber(),
                transaction.getValue(),
                transaction.getDate(),
                transaction.getStatus(),
                transaction.getAccount() != null ? AccountEntityMapper.mapToEntity(transaction.getAccount()) : null,
                transaction.getTransactionType() != null ? TransactionTypeEntityMapper.mapToEntity(transaction.getTransactionType()) : null);

    }

    public static Transaction mapToModel(TransactionEntity transactionEntity) {
        if (transactionEntity == null) {
            return null;
        }

        return new Transaction(
                transactionEntity.getId(),
                transactionEntity.getAccountNumber(),
                transactionEntity.getDetails(),
                transactionEntity.getValue(),
                transactionEntity.getDate(),
                transactionEntity.getStatus(),
                transactionEntity.getAccount() != null ? AccountEntityMapper.mapToModel(transactionEntity.getAccount()) : null,
                transactionEntity.getTransactionType() != null ? TransactionTypeEntityMapper.mapToModel(transactionEntity.getTransactionType()) : null);

    }

}