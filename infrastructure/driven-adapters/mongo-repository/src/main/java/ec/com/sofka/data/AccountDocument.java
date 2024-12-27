package ec.com.sofka.data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Account")
public class AccountDocument {
    @Id
    private int id;
    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[a-zA-Z0-9]{10,20}$", message = "Account number must be alphanumeric and between 10 and 20 characters")
    private String accountNumber;
    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "^(savings|current|fixed|credit)$", message = "Account type must be one of the following: savings, current, fixed, credit")
    private String accountType;
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0", message = "Balance must not be negative")
    private BigDecimal balance;
    @DBRef
    @NotNull(message = "Card is required")
    private List<CardDocument> cards;
    @DBRef
    @NotNull(message = "Transaction is required")
    private List<TransactionDocument> transactions;
}

