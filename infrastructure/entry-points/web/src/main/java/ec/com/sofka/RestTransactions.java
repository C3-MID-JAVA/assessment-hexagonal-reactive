package ec.com.sofka;

import ec.com.sofka.TransactionHandler;
import ec.com.sofka.data.RequestAccountDTO;
import ec.com.sofka.data.RequestTransactionDTO;
import ec.com.sofka.data.ResponseTransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org. springframework. web. reactive. function. server. RequestPredicates.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class RestTransactions {

    private final TransactionHandler handler;

    public RestTransactions(TransactionHandler handler) {
        this.handler = handler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/transactions",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "getAllTransactions", // Identificador único para la operación
                            summary = "Obtener todas las transacciones", // Título visible en la UI de Swagger
                            description = "Devuelve una lista con todas las transacciones registradas en el sistema.",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida exitosamente",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseTransactionDTO.class))),
                                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/transactions",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"Transactions"},
                            operationId = "createTransaction",
                            summary = "Crear una nueva transacción",
                            description = "Registra una nueva transacción asociada a una cuenta bancaria existente.",
                            requestBody = @RequestBody(
                                    description = "Details of the required entity.",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = RequestTransactionDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Transacción creada exitosamente",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseTransactionDTO.class))),
                                    @ApiResponse(responseCode = "404", description = "Cuenta bancaria no encontrada", content = @Content),
                                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
                            }
                    )
            )
    })

    public RouterFunction<ServerResponse> transactionRoutes() {
        return RouterFunctions
                .route(GET("/transactions"), this::getAllTransactions)
                .andRoute(POST("/transactions"), this::createTransaction);
    }

    public Mono<ServerResponse> getAllTransactions(ServerRequest request) {
        Flux<ResponseTransactionDTO> transactions = handler.getAllTransactions();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactions, ResponseTransactionDTO.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createTransaction(ServerRequest request) {
                return request.bodyToMono(RequestTransactionDTO.class)
                .flatMap(handler::createTransaction)
                .flatMap(transaction -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(transaction));
    }

}