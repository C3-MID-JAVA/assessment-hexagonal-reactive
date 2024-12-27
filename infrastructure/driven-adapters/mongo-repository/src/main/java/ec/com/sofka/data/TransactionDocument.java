package ec.com.sofka.data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Transaction")
public class TransactionDocument {
    @Id
    private int id;
    @DBRef
    @NotNull(message = "Branch is required")
    private BranchDocument branch;
    @NotNull(message = "Transaction date is required")
    private LocalDateTime date;
    @NotNull(message = "Transaction's type is required")
    @Pattern(regexp = "^(branch_transfer|another_account_deposit|store_card_purchase|online_card_purchase|atm_withdrawal|atm_deposit)$",
            message = "Invalid transaction type. Valid types are: branch_transfer, another_account_deposit, " +
                    "store_card_purchase, online_card_purchase, atm_withdrawal, atm_deposit")
    private String type;
    @NotNull(message = "Transaction amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
    @DBRef
    @NotNull(message = "Destination account is required")
    private AccountDocument destinationAccount;
    @DBRef
    @NotNull(message = "Destination account is required")
    private AccountDocument sourceAccount;
}
