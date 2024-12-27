package ec.com.sofka.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import ec.com.sofka.customException.AlreadyExistsException;
import ec.com.sofka.customException.InsufficientBalanceException;
import ec.com.sofka.customException.InvalidTypeException;
import ec.com.sofka.customException.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GlobalExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return switch (ex) {
            case MethodArgumentNotValidException methodArgumentNotValidException ->
                    handleValidationExceptions(methodArgumentNotValidException, exchange);
            case NotFoundException notFoundException -> handleNotFoundException(notFoundException, exchange);
            case InsufficientBalanceException insufficientBalanceException ->
                    handleInsufficientBalanceException(insufficientBalanceException, exchange);
            case InvalidTypeException invalidTransactionTypeException ->
                    handleInvalidTypeException(invalidTransactionTypeException, exchange);
            case AlreadyExistsException alreadyExistsException ->
                    handleAlreadyExistsException(alreadyExistsException, exchange);
            case NullPointerException nullPointerException ->
                    handleNullPointerException(nullPointerException, exchange);
            case IllegalArgumentException illegalArgumentException ->
                    handleIllegalArgument(illegalArgumentException, exchange);
            case HttpMessageNotReadableException httpMessageNotReadableException ->
                    handleHttpMessageNotReadable(httpMessageNotReadableException, exchange);
            case AccessDeniedException accessDeniedException -> handleAccessDenied(accessDeniedException, exchange);
            case InvalidFormatException invalidFormatException ->
                    handleInvalidFormatException(invalidFormatException, exchange);
            default -> handleGenericException((Exception) ex, exchange);
        };
    }

    private Mono<Void> handleValidationExceptions(MethodArgumentNotValidException ex, ServerWebExchange exchange) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ServerResponse.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errors)
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleNotFoundException(NotFoundException ex, ServerWebExchange exchange) {
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .bodyValue("The account could not be found: " + ex.getMessage())
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }


    private Mono<Void> handleInsufficientBalanceException(InsufficientBalanceException ex, ServerWebExchange exchange) {
        return ServerResponse.badRequest()
                .bodyValue("Balance validation failed: " + ex.getMessage())
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleInvalidTypeException(InvalidTypeException ex, ServerWebExchange exchange) {
        return ServerResponse.badRequest()
                .bodyValue("Invalid transaction type provided: " + ex.getMessage())
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleAlreadyExistsException(AlreadyExistsException ex, ServerWebExchange exchange) {
        return ServerResponse.badRequest()
                .bodyValue("Transaction already exists: " + ex.getMessage())
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleNullPointerException(NullPointerException ex, ServerWebExchange exchange) {
        String message = "An unexpected error occurred. Please contact support. Error: " + ex.getMessage();
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue(message)
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleIllegalArgument(IllegalArgumentException ex, ServerWebExchange exchange) {
        return ServerResponse.badRequest()
                .bodyValue("Illegal argument: " + ex.getMessage())
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, ServerWebExchange exchange) {
        return ServerResponse.badRequest()
                .bodyValue("Malformed JSON request: " + ex.getMessage())
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleAccessDenied(AccessDeniedException ex, ServerWebExchange exchange) {
        return ServerResponse.status(HttpStatus.FORBIDDEN)
                .bodyValue("Access denied: " + ex.getMessage())
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleGenericException(Exception ex, ServerWebExchange exchange) {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue("An unexpected error occurred: " + ex.getMessage() + ". Please contact support.")
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private Mono<Void> handleInvalidFormatException(InvalidFormatException ex, ServerWebExchange exchange) {
        return ServerResponse.badRequest()
                .bodyValue("Deserialization error: " + ex.getMessage())
                .flatMap(response -> response.writeTo(exchange, createContext(exchange)));
    }

    private ServerResponse.Context createContext(ServerWebExchange exchange) {
        return new ServerResponse.Context() {
            @Override
            public List<HttpMessageWriter<?>> messageWriters() {
                return HandlerStrategies.withDefaults().messageWriters();
            }

            @Override
            public List<ViewResolver> viewResolvers() {
                return HandlerStrategies.withDefaults().viewResolvers();
            }
        };
    }

}