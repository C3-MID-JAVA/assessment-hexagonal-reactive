package ec.com.sofka.router;

import ec.com.sofka.dto.request.AccountRequestDTO;
import ec.com.sofka.dto.response.AccountResponseDTO;
import ec.com.sofka.exception.RequestValidator;
import ec.com.sofka.exception.model.ErrorResponse;
import ec.com.sofka.handler.account.CreateAccountHandler;
import ec.com.sofka.handler.account.FindAccountByNumberHandler;
import ec.com.sofka.handler.account.GetAllAccountsHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AccountRouter {

    private final RequestValidator requestValidator;
    private final GetAllAccountsHandler getAllAccountsHandler;
    private final CreateAccountHandler createAccountHandler;
    private final FindAccountByNumberHandler findAccountByNumberHandler;

    public AccountRouter(RequestValidator requestValidator,
                         GetAllAccountsHandler getAllAccountsHandler,
                         CreateAccountHandler createAccountHandler,
                         FindAccountByNumberHandler findAccountByNumberHandler) {
        this.requestValidator = requestValidator;
        this.getAllAccountsHandler = getAllAccountsHandler;
        this.createAccountHandler = createAccountHandler;
        this.findAccountByNumberHandler = findAccountByNumberHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/accounts",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = CreateAccountHandler.class,
                    beanMethod = "save",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "createAccount",
                            summary = "Create a new account",
                            description = "Create a new account from the request data.",
                            requestBody = @RequestBody(
                                    description = "Details of the required entity.",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Record created successfully.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "422",
                                            description = "The entity has a conflict.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal application problems.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/accounts",
                    method = RequestMethod.GET,
                    beanClass = GetAllAccountsHandler.class,
                    beanMethod = "getAllAccounts",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "getAllAccounts",
                            summary = "Get all accounts",
                            description = "Get all registered accounts.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully obtained all registered accounts.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "No accounts found"
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal application problems.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/accounts/{accountNumber}",
                    method = RequestMethod.GET,
                    beanClass = FindAccountByNumberHandler.class,
                    beanMethod = "findByNumber",
                    operation = @Operation(
                            tags = {"Accounts"},
                            operationId = "findAccountByNumber",
                            summary = "Find account by number",
                            description = "Retrieve details of an account by its unique number.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved account details.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Account not found.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal application problems.",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> accountsRouters() {
        return RouterFunctions
                .route(POST("/api/accounts").and(accept(APPLICATION_JSON)), this::saveAccount)
                .andRoute(GET("/api/accounts").and(accept(APPLICATION_JSON)), this::allAccounts)
                .andRoute(GET("/api/accounts/{accountNumber}").and(accept(APPLICATION_JSON)), this::findAccountByNumber);
    }

    public Mono<ServerResponse> saveAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(requestValidator::validate)
                .flatMap(createAccountHandler::handle)
                .flatMap(response -> ServerResponse.status(201).contentType(APPLICATION_JSON).bodyValue(response));
    }

    public Mono<ServerResponse> allAccounts(ServerRequest request) {
        return getAllAccountsHandler.handle()
                .collectList()
                .flatMap(accounts -> {
                    if (accounts.isEmpty()) {
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(accounts);
                });
    }

    public Mono<ServerResponse> findAccountByNumber(ServerRequest request) {
        String accountNumber = request.pathVariable("accountNumber");
        return findAccountByNumberHandler.handle(accountNumber)
                .flatMap(account -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(account))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
