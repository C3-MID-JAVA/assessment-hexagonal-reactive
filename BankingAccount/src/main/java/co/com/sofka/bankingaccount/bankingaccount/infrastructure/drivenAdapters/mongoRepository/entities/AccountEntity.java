package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "bank_accounts")
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
public class AccountEntity {
    @Id
    private String id;

    private String entitledUser;

//    private String accountNumber;

    private BigDecimal balance;

    public AccountEntity(String id, String entitledUser, BigDecimal balance){
        this.id = id;
        this.entitledUser = entitledUser;
        this.balance = balance;
    }

    public void applyTransaction(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}
