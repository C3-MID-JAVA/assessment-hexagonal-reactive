package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.data.TransactionRequestDTO;
import ec.com.sofka.data.TransactionResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Component
public class TransactionDTOMapper {


    public static Transaction  transactionRequestToTransaction(TransactionRequestDTO transaction) {
        return new Transaction(
                null,
                transaction.getAmount(),
                null,
                null,
                transaction.getTransactionType(),
                transaction.getAccountNumber()
        );
    }

    public static TransactionResponseDTO transactionToTransactionResponse(Transaction transaction, BigDecimal balance, String accountNumber) {
        return new TransactionResponseDTO(
                transaction,
                balance,
                accountNumber
        );
    }

}
