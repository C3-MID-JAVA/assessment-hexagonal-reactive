package ec.com.sofka.mapper;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;

public class TransactionDTOMapper {
    public static Transaction toEntity(TransactionRequestDTO transactionRequestDTO){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setTransactionType(transactionRequestDTO.getTransactionType());
        transaction.setDescription(transactionRequestDTO.getDescription());
        return transaction;
    }

    public static TransactionResponseDTO fromEntity(Transaction transaction){
        return new TransactionResponseDTO(transaction.getId(), transaction.getAccount(),
                transaction.getFee(), transaction.getAmount(), transaction.getTransactionType(),
                transaction.getDate(), transaction.getDescription());
    }
}
