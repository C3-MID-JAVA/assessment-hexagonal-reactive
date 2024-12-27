package ec.com.sofka.data;

import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountResponseDTO implements Serializable {

    private String number;
    private BigDecimal availableBalance;
    private BigDecimal retainedBalance;
    private String status;

    private Mono<UserResponseDTO> user;

    public AccountResponseDTO(String number, BigDecimal availableBalance, BigDecimal retainedBalance, String status, Mono<UserResponseDTO> user) {
        this.number = number;
        this.availableBalance = availableBalance;
        this.retainedBalance = retainedBalance;
        this.status = status;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getRetainedBalance() {
        return retainedBalance;
    }

    public void setRetainedBalance(BigDecimal retainedBalance) {
        this.retainedBalance = retainedBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Mono<UserResponseDTO> getUser() {
        return user;
    }

    public void setUser(Mono<UserResponseDTO> user) {
        this.user = user;
    }

}