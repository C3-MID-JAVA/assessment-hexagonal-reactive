package co.com.sofkau.data;

import co.com.sofkau.ConstansTrType;
import jakarta.validation.constraints.NotNull;

public class AccountSimpleRequestDTO {

    @NotNull(message = "accountNumber" + ConstansTrType.NOT_NULL_FIELD)
    private String accountNumber;

    public AccountSimpleRequestDTO(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
