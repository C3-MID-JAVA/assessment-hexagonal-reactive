package ec.com.sofka.data;

import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TransactionResponseDTO implements Serializable {

    private String accountNumber;
    private BigDecimal value;
    private Date date;
    private String status;
    private Mono<AccountResponseDTO> account;
    private Mono<TransactionTypeResponseDTO> typeTransaction;

    public TransactionResponseDTO(String accountNumber, BigDecimal value, Date date, String status, Mono<AccountResponseDTO> account, Mono<TransactionTypeResponseDTO> typeTransaction) {
        this.accountNumber = accountNumber;
        this.value = value;
        this.date = date;
        this.status = status;
        this.account = account;
        this.typeTransaction = typeTransaction;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Mono<TransactionTypeResponseDTO> getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(Mono<TransactionTypeResponseDTO> typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Mono<AccountResponseDTO> getAccount() {
        return account;
    }

    public void setAccount(Mono<AccountResponseDTO> account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}