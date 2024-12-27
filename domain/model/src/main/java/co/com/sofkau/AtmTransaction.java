package co.com.sofkau;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AtmTransaction extends Transaction {

    private String atmName;
    private String operationType;


    public AtmTransaction(Card card, String id, BigDecimal amount, String description, String transactionType, BigDecimal transactionFee, LocalDateTime timestamp, Account account, String atmName, String operationType) {
        super(card, id, amount, description, transactionType, transactionFee, timestamp, account);
        this.atmName = atmName;
        this.operationType = operationType;
    }
     public AtmTransaction() {

     }

    @Override
    public void processTransaction() {
        if(this.operationType.equals("ATM_DEBIT")) {
            setTransactionFee(BigDecimal.valueOf(1));
        } else {
            setTransactionFee(BigDecimal.valueOf(2));
        }
    }

    public String getAtmName() {
        return atmName;
    }

    public void setAtmName(String atmName) {
        this.atmName = atmName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
