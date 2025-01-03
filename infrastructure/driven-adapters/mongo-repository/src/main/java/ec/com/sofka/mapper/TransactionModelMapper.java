package ec.com.sofka.mapper;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionEntity;

public class TransactionModelMapper {

  public static Transaction fromEntity(TransactionEntity transactionEntity) {
    return Transaction.create(
            transactionEntity.getId(),
            transactionEntity.getAccountId(),
            transactionEntity.getType(),
            transactionEntity.getAmount(),
            transactionEntity.getTransactionCost());
  }

  public static TransactionEntity toEntity(Transaction transaction) {
    return new TransactionEntity(
            transaction.getId(),
            transaction.getAccountId(),
            transaction.getType(),
            transaction.getAmount(),
            transaction.getTransactionCost());
  }
}