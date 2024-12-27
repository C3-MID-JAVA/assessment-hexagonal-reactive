package ec.com.sofka.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "accounts")
public class AccountEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(name = "number")
    private String number;

    @Field(name = "available_balance")
    private BigDecimal availableBalance;

    @Field(name = "retained_balance")
    private BigDecimal retainedBalance;

    @Field(name = "status")
    private String status;

    @Field(name = "user")
    private UserEntity user;

    public AccountEntity() {
    }

    public AccountEntity(String id, String number, BigDecimal availableBalance, BigDecimal retainedBalance, String status, UserEntity user) {
        this.id = id;
        this.number = number;
        this.availableBalance = availableBalance;
        this.retainedBalance = retainedBalance;
        this.status = status;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }
}