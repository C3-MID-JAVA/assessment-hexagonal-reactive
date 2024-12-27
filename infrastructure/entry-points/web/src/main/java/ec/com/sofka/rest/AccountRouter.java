package ec.com.sofka.rest;

import ec.com.sofka.data.AccountInDTO;
import ec.com.sofka.data.AccountOutDTO;
import ec.com.sofka.handler.AccountHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
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
@Tag(name = "Accounts", description = "API para la gestión de cuentas")
public class AccountRouter {

    private final AccountHandler accountHandler;

    public AccountRouter(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @Bean

    @RouterOperations({
            @RouterOperation(
                    path = "/v1/api/accounts",
                    operation = @Operation(
                            summary = "Crear una nueva cuenta",
                            description = "Crea una cuenta a partir de los datos proporcionados.",
                            requestBody = @RequestBody(description = "Datos de la cuenta", required = true),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
                                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts",
                    operation = @Operation(
                            summary = "Actualizar una cuenta",
                            description = "Actualiza los detalles de una cuenta existente.",
                            requestBody = @RequestBody(description = "Datos actualizados de la cuenta", required = true),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente"),
                                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts/{id}",
                    operation = @Operation(
                            summary = "Obtener cuenta por ID",
                            description = "Devuelve los detalles de una cuenta utilizando su ID.",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
                                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts",
                    operation = @Operation(
                            summary = "Obtener todas las cuentas",
                            description = "Devuelve una lista de todas las cuentas.",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Lista de cuentas encontrada")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> accountRouterBean() {
        return RouterFunctions.route()
                .POST("/v1/api/accounts", this::saveAccount)
                .PUT("/v1/api/accounts", this::updateAccount)
                .GET("/v1/api/accounts/{id}", this::getAccountById)
                .GET("/v1/api/accounts", this::getAllAccounts)
                .build();
    }

    @Operation(
            summary = "Crear una nueva cuenta",
            description = "Crea una cuenta a partir de los datos proporcionados en el cuerpo de la solicitud.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información de la cuenta",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountInDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountOutDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
            }
    )
    public Mono<ServerResponse> saveAccount(ServerRequest request) {
        return request.bodyToMono(AccountInDTO.class)
                .flatMap(accountHandler::saveAccount)
                .flatMap(accountOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountOutDTO));
    }

    public Mono<ServerResponse> updateAccount(ServerRequest request) {
        return request.bodyToMono(AccountInDTO.class)
                .flatMap(accountHandler::saveAccount)
                .flatMap(accountOutDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountOutDTO));
    }

    public Mono<ServerResponse> getAccountById(ServerRequest request) {
        String id = request.pathVariable("id");
        return accountHandler.findAccountById(id)
                .flatMap(accountOutDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountOutDTO))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return accountHandler.findAllAccounts()
                .collectList()
                .flatMap(accounts -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accounts));
    }
}
