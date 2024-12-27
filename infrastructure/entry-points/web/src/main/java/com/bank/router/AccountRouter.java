package com.bank.router;

import com.bank.data.AccountRequestDTO;
import com.bank.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

public class AccountRouter {
    private final AccountHandler accountHandler;

    public AccountRouter(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> accountRoutes() {
        return RouterFunctions
                .route(GET("/accounts"), this::getAllAccounts)
                .andRoute(GET("accounts/{id}"), this::getAccountById)
                .andRoute(POST("/accounts").and(accept(MediaType.APPLICATION_JSON)), this::createAccount);
    }

    public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return accountHandler.findAllAccounts()
                .collectList()
                .flatMap(accs -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accs));
    }

    public Mono<ServerResponse> getAccountById(ServerRequest request) {
        String accId = request.pathVariable("id");
        return accountHandler.findAccountById(accId).flatMap(accountResponseDTO -> ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(accountResponseDTO));
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountHandler::createAccount)
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO));
    }
}
