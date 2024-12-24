package ec.com.sofka.mapper;


import ec.com.sofka.Transaction;
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
}
