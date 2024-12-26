package ec.com.sofka.strategy;

import ec.com.sofka.Account;

import java.math.BigDecimal;

public class TransaccionStrategyContext {
    private final Account cuenta;
    private final TransaccionStrategy strategy;
    private final BigDecimal monto;

    public TransaccionStrategyContext(Account cuenta, TransaccionStrategy strategy, BigDecimal monto) {
        this.cuenta = cuenta;
        this.strategy = strategy;
        this.monto = monto;
    }

    public Account getCuenta() {
        return cuenta;
    }

    public TransaccionStrategy getStrategy() {
        return strategy;
    }

    public BigDecimal getMonto() {
        return monto;
    }
}
