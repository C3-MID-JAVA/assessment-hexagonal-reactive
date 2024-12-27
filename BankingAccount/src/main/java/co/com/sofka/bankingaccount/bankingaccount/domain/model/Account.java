package co.com.sofka.bankingaccount.bankingaccount.domain.model;

import lombok.*;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String id;
    private String entitledUser;
    private BigDecimal balance;
    private List<Transaction> transactions;
}
