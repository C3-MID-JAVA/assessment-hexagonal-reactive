package ec.com.sofka.data;

import jakarta.validation.constraints.NotNull;

public class RequestByPOST {
    @NotNull
    private String accountNumber;

    public RequestByPOST(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return accountNumber;
    }

    public void setId(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
