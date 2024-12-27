package ec.com.sofka;

import ec.com.sofka.data.dto.accountDTO.AccountRequestDTO;
import ec.com.sofka.data.dto.accountDTO.AccountResponseDTO;
import ec.com.sofka.handlers.account.GetAccountByIdHandler;
import ec.com.sofka.handlers.account.GetAllAccountsHandler;
import ec.com.sofka.handlers.account.SaveAccountHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RestAccount {
    private final GetAccountByIdHandler getAccountByIdHandler;
    private final GetAllAccountsHandler getAllAccountsHandler;
    private final SaveAccountHandler saveAccountHandler;
    private final Logger logger = LoggerFactory.getLogger(RestAccount.class);

    public RestAccount(GetAccountByIdHandler getAccountByIdHandler, GetAllAccountsHandler getAllAccountsHandler, SaveAccountHandler saveAccountHandler) {
        this.getAccountByIdHandler = getAccountByIdHandler;
        this.getAllAccountsHandler = getAllAccountsHandler;
        this.saveAccountHandler = saveAccountHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> accountRoutes() {
        return route(GET("/api/cuentas/{id}"), this::getAccountById)
                .andRoute(GET("/api/cuentas"), this::getAllAccounts)
                .andRoute(POST("/api/cuentas"), this::createAccount)
                .andRoute(GET("/api/**"), request ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue("The requested resource does not exist"));
    }

    public Mono<ServerResponse> getAccountById(ServerRequest request) {
        String id = request.pathVariable("id");
        logger.info("Received request for account ID: {}", id);

        return getAccountByIdHandler.getAccountById(id)
                .flatMap(responseDTO -> {
                    logger.info("Account found: {}", responseDTO);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(responseDTO);
                })
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                        .bodyValue("La cuenta con el ID " + id + " no existe"));
    }

    public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        logger.info("Received request for all accounts");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllAccountsHandler.getAccounts(), AccountResponseDTO.class);
    }

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMap(saveAccountHandler::handle)
                .flatMap(account -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(account))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .bodyValue(e.getMessage()));
    }
}