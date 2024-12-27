package co.com.sofka.bankingaccount.bankingaccount.infrastructure.entryPoints.web.routers;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.entryPoints.web.handlers.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TransactionRouter {
    @Bean
    public RouterFunction<ServerResponse> transactionRoutes(TransactionHandler handler) {
        return route()
                .POST("/transaction", handler::executeTransaction)  // Crear una transacciones por cuenta
                .build();
    }
}
