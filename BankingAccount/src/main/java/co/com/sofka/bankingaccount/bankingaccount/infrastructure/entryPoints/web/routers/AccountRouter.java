package co.com.sofka.bankingaccount.bankingaccount.infrastructure.entryPoints.web.routers;

import co.com.sofka.bankingaccount.bankingaccount.infrastructure.entryPoints.web.handlers.AccountHandler;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.entryPoints.web.handlers.TransactionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountRouter {
    @RouterOperations({
            @RouterOperation(
                    path = "/accounts",
                    beanClass = AccountHandler.class,
                    beanMethod = "createBankAccount",
                    operation = @Operation(
                            summary = "Crear una cuenta bancaria",
                            description = "Crea una nueva cuenta bancaria con un saldo inicial.",
                            requestBody = @RequestBody(description = "Detalles de la cuenta a crear"),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
                                    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/transaction",
                    beanClass = AccountHandler.class,
                    beanMethod = "executeTransaction",
                    operation = @Operation(
                            summary = "Ejecutar una transacción",
                            description = "Realiza una transacción sobre una cuenta específica.",
                            requestBody = @RequestBody(description = "Detalles de la transacción a realizar"),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Transacción ejecutada exitosamente"),
                                    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accountWithTransactions/{id}",
                    beanClass = AccountHandler.class,
                    beanMethod = "getAccountWithTransactions",
                    operation = @Operation(
                            summary = "Obtener cuentas con transacciones",
                            description = "Devuelve la información de todas las cuentas junto con sus transacciones.",
                            responses = @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida")
                    )
            ),
            @RouterOperation(
                    path = "/accounts/{id}",
                    beanClass = AccountHandler.class,
                    beanMethod = "getAccountById",
                    operation = @Operation(
                            summary = "Obtener cuenta específica",
                            description = "Devuelve la información de una cuenta específica por su ID.",
                            parameters = @Parameter(name = "id", description = "ID de la cuenta", required = true),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
                                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/accounts/{id}/transactions",
                    beanClass = AccountHandler.class,
                    beanMethod = "getTransactionsByAccount",
                    operation = @Operation(
                            summary = "Obtener transacciones por cuenta",
                            description = "Devuelve todas las transacciones relacionadas con una cuenta específica.",
                            parameters = @Parameter(name = "id", description = "ID de la cuenta", required = true),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida"),
                                    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> accountRoutes(AccountHandler handler) {
        return route()
                .POST("/accounts", handler::createBankAccount)  // Crear una cuenta bancaria
                .POST("/accounts/transaction", handler::executeTransaction)  // Ejecutar una transacción
                .GET("/accountWithTransactions", handler::getAccountWithTransactions)  // Obtener cuentas con transacciones
                .GET("/accounts/{id}", handler::getAccountById)  // Obtener cuenta específica
                .GET("/accounts/{id}/transactions", handler::getTransactionsByAccount)  // Obtener transacciones por cuenta
                .build();
    }


}
