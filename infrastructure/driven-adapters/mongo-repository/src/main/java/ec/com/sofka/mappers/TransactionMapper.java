package ec.com.sofka.mappers;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionEntity;

public class TransactionMapper {

    public static Transaction toModel(TransactionEntity transactionEntity){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionEntity.getAmount());
        transaction.setCost(transactionEntity.getCost());
        transaction.setIdAccount(transactionEntity.getIdAccount());
        transaction.setType(transactionEntity.getType());
        return transaction;
    }

    public static TransactionEntity toDocument(Transaction transaction){
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setCost(transaction.getCost());
        transactionEntity.setIdAccount(transaction.getIdAccount());
        transactionEntity.setType(transaction.getType());
        return transactionEntity;
    }
}
