package ec.com.sofka.router;

import ec.com.sofka.ConflictException;
import ec.com.sofka.ErrorResponse;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.handler.account.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration

public class AccountRouter {

    private final AccountHandler handler;
    private final Validator validator;

    public AccountRouter(AccountHandler handler, Validator validator) {
        this.handler = handler;
        this.validator = validator;
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
                .switchIfEmpty(ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .bodyValue("The account with the provided ID does not exist"));
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(accountRequestDTO -> {
                    // Crear un objeto para capturar los errores de validación
                    var bindingResult = new BeanPropertyBindingResult(accountRequestDTO, AccountRequestDTO.class.getName());
                    validator.validate(accountRequestDTO, bindingResult);

                    // Verificar si hay errores
                    if (bindingResult.hasErrors()) {
                        String errorMessage = bindingResult.getAllErrors().stream()
                                .map(error -> error.getDefaultMessage())
                                .collect(Collectors.joining(", "));
                        return ServerResponse
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new ErrorResponse(
                                        HttpStatus.BAD_REQUEST.value(),
                                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                        errorMessage,
                                        request.path()
                                ));
                    }

                    // Si no hay errores, proceder al handler
                    return handler.createAccount(accountRequestDTO)
                            .flatMap(accountResponseDTO -> ServerResponse
                                    .status(HttpStatus.CREATED)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(accountResponseDTO))
                            .onErrorResume(ConflictException.class, ex -> ServerResponse
                                    .status(HttpStatus.CONFLICT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(new ErrorResponse(
                                            HttpStatus.CONFLICT.value(),
                                            HttpStatus.CONFLICT.getReasonPhrase(),
                                            ex.getMessage(),
                                            request.path()
                                    )));
                });
    }


}
