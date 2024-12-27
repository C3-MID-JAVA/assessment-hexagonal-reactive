package ec.com.sofka.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {
    private int id;
    private BranchResponseDTO branch;
    private LocalDateTime date;
    private String type;
    private BigDecimal amount;
    private String description;
    private AccountResponseDTO destinationAccount;
    private AccountResponseDTO sourceAccount;
}
