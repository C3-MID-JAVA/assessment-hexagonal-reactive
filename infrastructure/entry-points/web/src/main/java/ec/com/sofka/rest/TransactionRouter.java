package ec.com.sofka.rest;

import ec.com.sofka.data.TransactionInDTO;
import ec.com.sofka.handler.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class TransactionRouter {

    private final TransactionHandler transactionHandler;

    public TransactionRouter(TransactionHandler transactionHandler) {
        this.transactionHandler = transactionHandler;
    }


    @Bean
    public RouterFunction<ServerResponse> TransactionRouterBean() {
        return RouterFunctions.route()
                .POST("/v1/api/movimientos/deposito/sucursal", this::makeBranchDeposit)
                .POST("/v1/api/movimientos/deposito/cajero", this::makeATMDeposit)
                .POST("/v1/api/movimientos/deposito/otra-cuenta", this::makeDepositToAnotherAccount)
                .POST("/v1/api/movimientos/compra/fisica", this::makePhysicalPurchase)
                .POST("/v1/api/movimientos/compra/web", this::makeOnlinePurchase)
                .POST("/v1/api/movimientos/retiro/cajero", this::makeATMWithdrawal)
                .build();
    }

    public Mono<ServerResponse> makeBranchDeposit(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeBranchDeposit)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makeATMDeposit(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeATMDeposit)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makeDepositToAnotherAccount(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeDepositToAnotherAccount)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makePhysicalPurchase(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makePhysicalPurchase)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makeOnlinePurchase(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeOnlinePurchase)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }

    public Mono<ServerResponse> makeATMWithdrawal(ServerRequest request) {
        return request.bodyToMono(TransactionInDTO.class)
                .flatMap(transactionHandler::makeATMWithdrawal)
                .flatMap(transactionOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transactionOutDTO));
    }
}
