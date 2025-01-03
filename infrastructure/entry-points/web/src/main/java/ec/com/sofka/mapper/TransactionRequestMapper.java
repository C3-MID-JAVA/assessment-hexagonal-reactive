package ec.com.sofka.mapper;

import ec.com.sofka.Transaction;
import ec.com.sofka.dto.response.TransactionResponseDTO;
import ec.com.sofka.dto.request.TransactionRequestDTO;

import java.math.BigDecimal;

public class TransactionRequestMapper {

    public static Transaction mapToModel(TransactionRequestDTO dto) {
        return Transaction.create(null, dto.getAccountId(), dto.getTransactionType(), dto.getTransactionAmount(), BigDecimal.ZERO);
    }

    public static TransactionResponseDTO mapToDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTransactionCost()
        );
    }
}
