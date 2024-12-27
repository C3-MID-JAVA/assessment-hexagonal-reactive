package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "transactions")
@Getter
@Setter
@NoArgsConstructor
public abstract class TransactionEntity {
    @Id
    private String id;

    private BigDecimal amount;
    private BigDecimal cost;
    private String type; //Added for MongoDB

    @Indexed
    private String accountId;

    public TransactionEntity(BigDecimal amount, String accountId, String type) {
        this.amount = amount;
        this.accountId = accountId;
        this.type = type;
    }

    public abstract BigDecimal calculateImpact();
}
