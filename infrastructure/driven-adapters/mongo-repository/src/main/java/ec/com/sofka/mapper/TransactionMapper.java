package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.data.TransactionEntity;

public class TransactionMapper {

    public static Transaction transactionEntityToTransaction(TransactionEntity transactionEntity) {
        return new Transaction(
                transactionEntity.getId(),
                transactionEntity.getAmount(),
                transactionEntity.getTransactionCost(),
                transactionEntity.getDate(),
                transactionEntity.getType(),
                transactionEntity.getAccountId()
        );
    }

    public static TransactionEntity transactionToAccountEntity(Transaction transaction) {
        return new TransactionEntity(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTransactionCost(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAccountId()
        );
    }

}
