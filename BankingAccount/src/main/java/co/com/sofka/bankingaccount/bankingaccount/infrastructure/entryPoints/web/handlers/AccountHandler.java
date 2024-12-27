package co.com.sofka.bankingaccount.bankingaccount.infrastructure.entryPoints.web.handlers;

import co.com.sofka.bankingaccount.bankingaccount.application.dto.request.CreateAccountRequestDTO;
import co.com.sofka.bankingaccount.bankingaccount.domain.model.Account;
import co.com.sofka.bankingaccount.bankingaccount.domain.model.Transaction;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.AccountUseCase;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.GetAccountWithTransactionsUseCase;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.TransactionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Tag(name = "Account API", description = "API para manejo de cuentas bancarias")
@Component
public class AccountHandler {
    private final AccountUseCase accountUseCase;
    private final TransactionUseCase transactionUseCase;
    private final GetAccountWithTransactionsUseCase getAccountWithTransactionsUseCase;

    public AccountHandler(AccountUseCase accountUseCase, TransactionUseCase transactionUseCase, GetAccountWithTransactionsUseCase getAccountWithTransactionsUseCase) {
        this.accountUseCase = accountUseCase;
        this.transactionUseCase = transactionUseCase;
        this.getAccountWithTransactionsUseCase = getAccountWithTransactionsUseCase;
    }

    // Crear cuenta bancaria
    @Operation(summary = "Crear una cuenta bancaria", description = "Crea una nueva cuenta bancaria con el saldo inicial.")
    public Mono<ServerResponse> createBankAccount(ServerRequest request) {
        return request.bodyToMono(Account.class)
                .flatMap(accountUseCase::createAccount)
                .flatMap(account -> ServerResponse
                        .status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(account));
    }

    // Ejecutar transacción
    @Operation(summary = "Ejecutar Transacción", description = "Ejecuta una transacción.")
    public Mono<ServerResponse> executeTransaction(ServerRequest request) {
        return request.bodyToMono(Transaction.class)
                .flatMap(transactionUseCase::createTransaction)
                .flatMap(transaction -> ServerResponse
                        .status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transaction));
    }

    // Obtener cuenta con transacciones
    @Operation(summary = "Obtener cuenta con transacciones", description = "Obtiene la información de una cuenta y sus transacciones asociadas.")
    public Mono<ServerResponse> getAccountWithTransactions(ServerRequest request) {
        String accountId = request.queryParam("accountId").orElse("");

        return getAccountWithTransactionsUseCase.execute(accountId)
                .flatMap(account -> ServerResponse.ok().bodyValue(account))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(e.getMessage()));
    }

    // Obtener cuenta específica por ID
    @Operation(summary = "Obtener cuenta por Id", description = "Obtiene la información de una cuenta por su id.")
    public Mono<ServerResponse> getAccountById(ServerRequest request) {
        String accountId = request.pathVariable("id");
        return accountUseCase.getAccountById(accountId)
                .flatMap(account -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(account))
                .onErrorResume(e -> ServerResponse
                        .status(404)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(e.getMessage()));
    }

    // Obtener transacciones por cuenta
    @Operation(summary = "Obtener transacciones por id de cuenta", description = "Obtiene la información de una cuenta y sus transacciones asociadas.")
    public Mono<ServerResponse> getTransactionsByAccount(ServerRequest request) {
        String accountId = request.pathVariable("id");
        return transactionUseCase.getTransactionsByBankAccount(accountId)
                .collectList()
                .flatMap(transactions -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactions));
    }
}
