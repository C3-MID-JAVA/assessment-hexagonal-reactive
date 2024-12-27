package ec.com.sofka.usecases.transaction;
import ec.com.sofka.Account;
import ec.com.sofka.Transaction;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
public class CreateTransactionUseCase {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public CreateTransactionUseCase(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Mono<Transaction> validarTransaction2(Account cuenta, BigDecimal monto, String tipo, BigDecimal costo, boolean esRetiro) {
        if (esRetiro && cuenta.getBalance().compareTo(monto.add(costo)) < 0) {
            throw new RuntimeException("Saldo insuficiente para realizar esta transacción");
        }
        actualizarSaldo(cuenta, monto, costo, tipo, esRetiro);
        //System.out.println("Cuenta actualizada antes " + cuenta.getId() + "  " + cuenta.getBalance() + "  " + cuenta.getOwner() + "  " + cuenta.getAccountNumber());

        Transaction transaction = new Transaction();
        transaction.setAmount(monto);
        transaction.setType(tipo);
        transaction.setCost(costo);
        transaction.setIdAccount(cuenta.getId());

        Account cuentaActualizada = new Account();
        cuentaActualizada.setId(cuenta.getId());
        cuentaActualizada.setOwner(cuenta.getOwner());
        cuentaActualizada.setBalance(cuenta.getBalance());
        cuentaActualizada.setAccountNumber(cuenta.getAccountNumber());
        //System.out.println("Cuenta actualizada " + cuentaActualizada.getId() + "  " + cuentaActualizada.getBalance() + "  " + cuentaActualizada.getOwner() + "  " + cuentaActualizada.getAccountNumber());

        return accountRepository.saveAccount(cuentaActualizada)
                .flatMap(savedAccount -> {
                    //System.out.println("Cuenta guardada " + savedAccount.getId() + "  " + savedAccount.getBalance() + "  " + savedAccount.getOwner() + "  " + savedAccount.getAccountNumber());
                    return transactionRepository.saveTransaction(transaction)
                            .switchIfEmpty(Mono.error(new RuntimeException("Transaccion no fue creada")));
                });
    }

    private void actualizarSaldo(Account cuenta, BigDecimal monto, BigDecimal costo, String tipo, boolean esRetiro) {
        if(esRetiro){
            cuenta.setBalance(cuenta.getBalance().subtract(monto.add(costo)));
        }else{
            cuenta.setBalance(cuenta.getBalance().add(monto).subtract(costo));
        }
    }

}


