package ec.com.sofka;

import ec.com.sofka.data.RequestAccountDTO;
import ec.com.sofka.data.ResponseAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;  // Para predicados como GET, POST

@Configuration
public class RestAccount {


    private final AccountHandler handler;

    public RestAccount(AccountHandler handler) {
        this.handler = handler;
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(
                    path = "/accounts/{id}",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getTransactionById",
                            summary = "Get transaction by ID",
                            description = "Fetches the transaction details associated with the given transaction ID.",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "The transaction ID to retrieve transaction details",
                                            required = true,
                                            in = ParameterIn.PATH
                                    )
                            }

                    )
            ),

            @RouterOperation(
                    path = "/accounts/",
                    operation = @Operation(
                                tags = {"Accounts"},
                                operationId = "createUser",
                                summary = "Create a new user",
                                description = "Create a new user from the request data.",
                                method = "POST",
                                requestBody = @RequestBody(
                                        description = "Details of the required entity.",
                                        required = true,
                                        content = @Content(
                                                mediaType = "application/json",
                                                schema = @Schema(implementation = RequestAccountDTO.class)
                                        )
                                ),
                                responses = {
                                        @ApiResponse(responseCode = "201", description = "Cuenta bancaria creada exitosamente",
                                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestAccountDTO.class))),
                                        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
                                }
                            )
            )
    })
    public RouterFunction<ServerResponse> cuentaRoutes() {
        return RouterFunctions
                .route(GET("/accounts/{id}"), this::getAccountById)
                .andRoute(POST("/accounts/"), this::createAccount);
    }


    public Mono<ServerResponse> getAccountById(ServerRequest request) {
        String id = request.pathVariable("id");
        return handler.getAccountById(id)
                .flatMap(account -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(account))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(RequestAccountDTO.class)
                .flatMap(handler::createAccount)
                .flatMap(account -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(account));
    }

}