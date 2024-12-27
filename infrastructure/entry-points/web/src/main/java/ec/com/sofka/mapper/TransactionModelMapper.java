package ec.com.sofka.mapper;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TransactionModelMapper {

    public static Transaction mapToModel(TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(transactionRequestDTO.getAccountNumber());
        transaction.setDetails(transactionRequestDTO.getDetails());
        transaction.setDate(transactionRequestDTO.getDate());
        transaction.setValue(transactionRequestDTO.getValue());
        transaction.setStatus(transactionRequestDTO.getStatus());

        return transaction;
    }

    public static TransactionResponseDTO mapToDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionResponseDTO(
                transaction.getAccountNumber(),
                transaction.getValue(),
                transaction.getDate(),
                transaction.getStatus(),
                transaction.getAccount() != null ? Mono.just(AccountModelMapper.mapToDTO(transaction.getAccount())) : null,
                transaction.getTransactionType() != null ? Mono.just(TransactionTypeModelMapper.mapToDTO(transaction.getTransactionType())) : null
        );
    }

}