package co.com.sofkau;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentWebTransaction extends Transaction {
    private String website;

    public PaymentWebTransaction(Card card, String id, BigDecimal amount, String description, String transactionType, BigDecimal transactionFee, LocalDateTime timestamp, Account account, String website) {
        super(card, id, amount, description, transactionType, transactionFee, timestamp, account);
        this.website = website;
    }

    public PaymentWebTransaction() {
    }

    @Override
    public void processTransaction() {
        setTransactionFee(BigDecimal.valueOf(0));
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
