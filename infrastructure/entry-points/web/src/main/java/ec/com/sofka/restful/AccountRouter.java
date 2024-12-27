package ec.com.sofka.restful;

import ec.com.sofka.data.AccountInDTO;
import ec.com.sofka.handler.AccountHandler;
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
public class AccountRouter {

    private final AccountHandler accountHandler;

    public AccountRouter(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> accountRouterBean() {
        return RouterFunctions.route()
                .POST("/v1/api/accounts", this::saveAccount)
                .PUT("/v1/api/accounts", this::updateAccount)
                .GET("/v1/api/accounts/{id}", this::getAccountById)
                .GET("/v1/api/accounts", this::getAllAccounts)
                .build();
    }

    private Mono<ServerResponse> saveAccount(ServerRequest request) {
        return request.bodyToMono(AccountInDTO.class)
                .flatMap(accountHandler::saveAccount)
                .flatMap(accountOutDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountOutDTO));
    }

    private Mono<ServerResponse> updateAccount(ServerRequest request) {
        return request.bodyToMono(AccountInDTO.class)
                .flatMap(accountHandler::saveAccount)
                .flatMap(accountOutDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountOutDTO));
    }

    private Mono<ServerResponse> getAccountById(ServerRequest request) {
        String id = request.pathVariable("id");
        return accountHandler.findAccountById(id)
                .flatMap(accountOutDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accountOutDTO))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return accountHandler.findAllAccounts()
                .collectList()
                .flatMap(accounts -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(accounts));
    }
}
