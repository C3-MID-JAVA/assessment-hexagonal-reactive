package co.com.sofka.bankingaccount.bankingaccount.domain.model;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Transaction {
    private String id;
    private BigDecimal amount;
    private BigDecimal cost;
    private String type; //Added for MongoDB
    private String accountId;

    public Transaction(BigDecimal amount, String accountId, String type ) {
        this.amount = amount;
        this.accountId = accountId;
        this.type = type;
    }

    public Transaction(String id, BigDecimal amount, String accountId, String type ) {
        this.id = id;
        this.amount = amount;
        this.accountId = accountId;
        this.type = type;
    }

    public Transaction(String id, BigDecimal amount, String accountId){
        this.id = id;
        this.amount = amount;
        this.accountId = accountId;
    }

    public abstract BigDecimal calculateImpact();
}
