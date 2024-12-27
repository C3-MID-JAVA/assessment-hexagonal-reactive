package co.com.sofkau;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BranchDeposit extends Transaction {
    private String branchName;

    public BranchDeposit() {
    }

    public BranchDeposit(Card card, String id, BigDecimal amount, String description, String transactionType, BigDecimal transactionFee, LocalDateTime timestamp, Account account, String website) {
        super(card, id, amount, description, transactionType, transactionFee, timestamp, account);
        this.branchName = website;
    }

    @Override
    public void processTransaction() {
        setTransactionFee(BigDecimal.valueOf(0));
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
