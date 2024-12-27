package ec.com.sofka.handlers.transaction;

import ec.com.sofka.Account;
import ec.com.sofka.enums.TypeTransaction;
import ec.com.sofka.exceptions.CuentaNoEncontradaException;
import ec.com.sofka.handlers.account.GetAccountByIdHandler;
import ec.com.sofka.usecases.account.CreateAccountUseCase;
import ec.com.sofka.usecases.transaction.CreateTransactionUseCase;
import ec.com.sofka.data.dto.transactionDTO.TransactionRequestDTO;
import ec.com.sofka.data.dto.transactionDTO.TransactionResponseDTO;
import ec.com.sofka.Transaction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class SaveTransactionHandler {
    private final CreateTransactionUseCase createTransactionUseCase;
    private final GetAccountByIdHandler getAccountByIdHandler;

    public SaveTransactionHandler(CreateTransactionUseCase createTransactionUseCase, GetAccountByIdHandler getAccountByIdHandler) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.getAccountByIdHandler = getAccountByIdHandler;
    }

    public Mono<TransactionResponseDTO> handle(TransactionRequestDTO transactionRequestDTO) {
        BigDecimal costo = obtenerCostoTipoTransaccion(transactionRequestDTO.getType());
        boolean esRetiro = esRetiro(transactionRequestDTO.getType());

        return validarAccount(transactionRequestDTO.getIdAccount())
                .flatMap(account -> createTransactionUseCase.validarTransaction2(account, transactionRequestDTO.getAmount(), transactionRequestDTO.getType(), costo, esRetiro))
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getCost(),
                        transaction.getIdAccount()
                ));
    }

    private BigDecimal obtenerCostoTipoTransaccion(String tipo) {
        if (!TypeTransaction.validadorTipo.validar(tipo)) {
            throw new IllegalArgumentException("Tipo de transacción no válido: " + tipo);
        }
        return TypeTransaction.fromString(tipo).getCosto();
    }
/*
    private Account validarAccount(String cuentaId) {
        return getAccountByIdHandler.getAccountById(cuentaId)
                .switchIfEmpty(Mono.error(new CuentaNoEncontradaException("Cuenta no encontrada con ID: " + cuentaId)))
                .map(accountResponseDTO -> {
                    Account account = new Account();
                    account.setBalance(accountResponseDTO.getBalance());
                    account.setAccountNumber(accountResponseDTO.getAccountNumber());
                    account.setOwner(accountResponseDTO.getOwner());
                    // Map other fields as necessary
                    return account;
                })
                .block();
    }*/
private Mono<Account> validarAccount(String cuentaId) {
    return getAccountByIdHandler.getAccountById(cuentaId)
            .switchIfEmpty(Mono.error(new CuentaNoEncontradaException("Cuenta no encontrada con ID: " + cuentaId)))
            .map(accountResponseDTO -> {
                Account account = new Account();
                account.setId(accountResponseDTO.getId());
                account.setBalance(accountResponseDTO.getBalance());
                account.setAccountNumber(accountResponseDTO.getAccountNumber());
                account.setOwner(accountResponseDTO.getOwner());
                System.out.println("Cuenta validada"+account.getId() +"  " + account.getBalance()+"  "+account.getOwner()+"  "+account.getAccountNumber());
                // Map other fields as necessary
                return account;
            });
}

    private boolean esRetiro(String tipo) {
        return tipo.startsWith("RETIRO") || tipo.equals("COMPRA_WEB") || tipo.equals("COMPRA_ESTABLECIMIENTO");
    }
}