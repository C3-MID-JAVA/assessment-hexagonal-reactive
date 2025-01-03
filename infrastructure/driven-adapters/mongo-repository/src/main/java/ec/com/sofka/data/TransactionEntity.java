package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("transactions")
public class TransactionEntity {
    @Id
    private String id;
    private String accountId;
    private String type;
    private BigDecimal amount;
    private BigDecimal transactionCost;

    public TransactionEntity(String id, String accountId, String type, BigDecimal amount, BigDecimal transactionCost) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.transactionCost = transactionCost;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }
}