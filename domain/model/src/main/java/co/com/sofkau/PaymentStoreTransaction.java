package co.com.sofkau;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentStoreTransaction extends Transaction {
    private String marketName;


    public PaymentStoreTransaction(Card card, String id, BigDecimal amount, String description, String transactionType, BigDecimal transactionFee, LocalDateTime timestamp, Account account, String marketName) {
        super(card, id, amount, description, transactionType, transactionFee, timestamp, account);
        this.marketName = marketName;
    }

    public PaymentStoreTransaction() {
    }

    @Override
    public void processTransaction() {
        setTransactionFee(BigDecimal.valueOf(0));
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }
}
