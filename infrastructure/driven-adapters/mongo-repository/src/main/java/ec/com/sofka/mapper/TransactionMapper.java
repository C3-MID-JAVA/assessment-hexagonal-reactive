package ec.com.sofka.mapper;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionEntity;

public class TransactionMapper {

    public static Transaction toTransaction(TransactionEntity transactionEntity){
        return new Transaction(transactionEntity.getId(), transactionEntity.getTransactionType(),
                transactionEntity.getAmount(), transactionEntity.getFee(), transactionEntity.getDate(),
                transactionEntity.getDescription(), AccountMapper.toAccount(transactionEntity.getAccount()));
    }

    public static TransactionEntity toTransactionEntity(Transaction transaction){
        return new TransactionEntity(transaction.getId(), transaction.getTransactionType(),
                transaction.getAmount(), transaction.getFee(), transaction.getDate(),
                transaction.getDescription(), AccountMapper.toAccountEntity(transaction.getAccount()));
    }
}
