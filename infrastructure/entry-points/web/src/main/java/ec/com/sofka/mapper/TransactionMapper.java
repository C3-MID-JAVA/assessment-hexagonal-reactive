package ec.com.sofka.mapper;


import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionInDTO;
import ec.com.sofka.data.TransactionOutDTO;

import java.time.LocalDate;

public class TransactionMapper {

    public static Transaction toEntity(TransactionInDTO transactionInDTO) {
        if (transactionInDTO == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setDescription(transactionInDTO.getDescription());
        transaction.setAmount(transactionInDTO.getAmount());
        transaction.setAccountId(transactionInDTO.getAccountId());

        return transaction;
    }

    public static TransactionOutDTO toDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionOutDTO transactionOutDTO = new TransactionOutDTO();
        transactionOutDTO.setId(transaction.getId());
        transactionOutDTO.setDescription(transaction.getDescription());
        transactionOutDTO.setAmount(transaction.getAmount());
        transactionOutDTO.setTransactionType("");
        transactionOutDTO.setDate(LocalDate.now());
        transactionOutDTO.setAccountId(transaction.getAccountId());

        return transactionOutDTO;
    }
}
