package ec.com.sofka.rest;

import ec.com.sofka.data.AccountInDTO;
import ec.com.sofka.data.AccountOutDTO;
import ec.com.sofka.handler.AccountHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import org.springframework.web.bind.annotation.RequestMethod;
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
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "saveAccount",
                            summary = "Create new account",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AccountInDTO.class))),
                            responses = {@ApiResponse(responseCode = "201", description = "Account created")}
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            operationId = "updateAccount",
                            summary = "Update account",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = AccountInDTO.class)))
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts/{id}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getAccountById",
                            summary = "Get account by ID",
                            parameters = @Parameter(name = "id", in = ParameterIn.PATH)
                    )
            ),
            @RouterOperation(
                    path = "/v1/api/accounts",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getAllAccounts",
                            summary = "Get all accounts"
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
