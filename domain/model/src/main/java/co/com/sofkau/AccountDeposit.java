package co.com.sofkau;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDeposit extends Transaction {

    private Account accountReceiver;

    public AccountDeposit(Card card, String id, BigDecimal amount, String description, String transactionType, BigDecimal transactionFee, LocalDateTime timestamp, Account account, Account accountReceiver) {
        super(card, id, amount, description, transactionType, transactionFee, timestamp, account);
        this.accountReceiver = accountReceiver;
    }

    public AccountDeposit() {
    }

    @Override
    public void processTransaction() {
        setTransactionFee(BigDecimal.valueOf(1.5));
    }

    public Account getAccountReceiver() {
        return accountReceiver;
    }

    public void setAccountReceiver(Account accountReceiver) {
        this.accountReceiver = accountReceiver;
    }

}
