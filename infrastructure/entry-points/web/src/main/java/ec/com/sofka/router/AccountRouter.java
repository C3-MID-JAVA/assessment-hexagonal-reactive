package ec.com.sofka.router;

import ec.com.sofka.ConflictException;
import ec.com.sofka.ErrorResponse;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.globalexceptions.GlobalErrorHandler;
import ec.com.sofka.handler.account.AccountHandler;
import ec.com.sofka.service.ValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration

public class AccountRouter {

    private final AccountHandler handler;
    private final ValidationService validationService;
    private final GlobalErrorHandler globalErrorHandler;

    public AccountRouter(AccountHandler handler, ValidationService validationService,GlobalErrorHandler globalErrorHandler) {
        this.handler = handler;
        this.validationService = validationService;
        this.globalErrorHandler = globalErrorHandler;
    }


    @Bean
    public RouterFunction<ServerResponse> cuentaRoutes() {
        return RouterFunctions
                .route(POST("/accounts").and(accept(MediaType.APPLICATION_JSON)), this::createAccount)
                //.andRoute(GET("/cuentas/listar").and(accept(MediaType.APPLICATION_JSON)), this::obtenerCuentas)
                .andRoute(POST("/accounts/getByAccountNumber").and(accept(MediaType.APPLICATION_JSON)), this::getAccountByAccountNumber)
                //.andRoute(POST("/cuentas/listar/saldoById").and(accept(MediaType.APPLICATION_JSON)), this::consultarSaldo);

        ;
    }

    public Mono<ServerResponse> getAccountByAccountNumber(ServerRequest request) {

        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountRequestDTO -> handler.getAccountByAccountNumber(accountRequestDTO.getAccountNumber()))
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex)); // Delegación genérica

    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(dto -> validationService.validate(dto, AccountRequestDTO.class)) // Valida el DTO
                .flatMap(handler::createAccount) // Lógica de negocio
                .flatMap(accountResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountResponseDTO))
                .onErrorResume(ex -> globalErrorHandler.handleException(request.exchange(), ex)); // Delegación genérica
    }







}
