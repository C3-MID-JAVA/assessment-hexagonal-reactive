package ec.com.sofka.mapper;


import ec.com.sofka.Transaction;
import ec.com.sofka.dto.TransactionRequestDTO;
import ec.com.sofka.dto.TransactionResponseDTO;

public class TransactionMapper {
    public static TransactionResponseDTO fromEntity(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getFee(),
                transaction.getNetAmount(),
                transaction.getType(),
                transaction.getTimestamp()
        );
    }

    public static Transaction toEntity(TransactionRequestDTO transactionRequestDTO){
        return new Transaction(
                transactionRequestDTO.getAmount(),
                transactionRequestDTO.getType(),
                transactionRequestDTO.getAccountNumber()
        );
    }
}
