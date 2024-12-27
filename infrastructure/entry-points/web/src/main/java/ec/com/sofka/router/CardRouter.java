package ec.com.sofka.router;

import ec.com.sofka.data.request.AccountRequestDTO;
import ec.com.sofka.data.request.CardRequestDTO;
import ec.com.sofka.data.response.CardResponseDTO;
import ec.com.sofka.handler.CardHandler;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Tag(name = "Card Management", description = "Endpoints for managing cards")
@Configuration
public class CardRouter {

    private final CardHandler cardHandler;

    public CardRouter(CardHandler cardHandler) {
        this.cardHandler = cardHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/cards/create",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"cards"},
                            operationId = "createCard",
                            summary = "Create a new card",
                            description = "Add a new card to the system",
                            requestBody = @RequestBody(
                                    description = "Card creation details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CardRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Card created successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CardResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid input data")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/cards/id",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"cards"},
                            operationId = "getCardById",
                            summary = "Retrieve a card by ID",
                            description = "Get details of a card by its unique ID",
                            requestBody = @RequestBody(
                                    description = "Card details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CardRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Card retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CardResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Card not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/cards/number",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"cards"},
                            operationId = "getCardByNumber",
                            summary = "Retrieve a card by number",
                            description = "Get details of a card by its unique number",
                            requestBody = @RequestBody(
                                    description = "Card details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CardRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Card retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CardResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Card not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/cards",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            tags = {"cards"},
                            operationId = "getAllCards",
                            summary = "Retrieve all cards",
                            description = "Get a list of all available cards",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Cards retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CardResponseDTO[].class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "No cards found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/cards/account",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"cards"},
                            operationId = "getCardsByAccountId",
                            summary = "Retrieve cards by account ID",
                            description = "Get all cards associated with a specific account",
                            requestBody = @RequestBody(
                                    description = "Account details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AccountRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Cards retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CardResponseDTO[].class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "No cards found for the account")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/cards/type",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"cards"},
                            operationId = "getCardsByType",
                            summary = "Retrieve cards by type",
                            description = "Get all cards of a specific type",
                            requestBody = @RequestBody(
                                    description = "Card details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CardRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Cards retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CardResponseDTO[].class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "No cards of the specified type found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/cards",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            tags = {"cards"},
                            operationId = "updateCard",
                            summary = "Update a card",
                            description = "Update details of an existing card",
                            requestBody = @RequestBody(
                                    description = "Updated card details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CardRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Card updated successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CardResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid input data"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Card not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/cards/delete",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"cards"},
                            operationId = "deleteCard",
                            summary = "Delete a card",
                            description = "Remove a card from the system by its ID",
                            requestBody = @RequestBody(
                                    description = "Deleted card details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CardRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "Card deleted successfully"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Card not found")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> cardRoutes() {
        return route(POST("/cards/create"), this::createCard)
                .andRoute(POST("/cards/id"), this::getCardById)
                .andRoute(POST("/cards/number"), this::getCardByNumber)
                .andRoute(GET("/cards"), this::getAllCards)
                .andRoute(POST("/cards/account"), this::getCardsByAccount)
                .andRoute(POST("/cards/type"), this::getCardsByType)
                .andRoute(PUT("/cards"), this::updateCard)
                .andRoute(POST("/cards/delete"), this::deleteCard);
    }

    private Mono<ServerResponse> createCard(ServerRequest request) {
        return request.bodyToMono(CardRequestDTO.class)
                .flatMap(cardHandler::createCard)
                .flatMap(cardResponseDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(cardResponseDTO));
    }

    private Mono<ServerResponse> getCardById(ServerRequest request) {
        return request.bodyToMono(CardRequestDTO.class)
                .flatMap(cardHandler::getCardById)
                .flatMap(cardResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(cardResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> getCardByNumber(ServerRequest request) {
        return request.bodyToMono(CardRequestDTO.class)
                .flatMap(cardHandler::getCardByNumber)
                .flatMap(cardResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(cardResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> getAllCards(ServerRequest request) {
        return cardHandler.getAllCards()
                .collectList()
                .flatMap(cardResponseDTOs -> ServerResponse.status(cardResponseDTOs.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(cardResponseDTOs));
    }

    private Mono<ServerResponse> getCardsByAccount(ServerRequest request) {
        return request.bodyToMono(AccountRequestDTO.class)
                .flatMapMany(cardHandler::getCardsByAccountId)
                .collectList()
                .flatMap(cardResponseDTOs -> ServerResponse.status(cardResponseDTOs.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(cardResponseDTOs));
    }

    private Mono<ServerResponse> getCardsByType(ServerRequest request) {
        return request.bodyToMono(CardRequestDTO.class)
                .flatMapMany(cardHandler::getCardsByType)
                .collectList()
                .flatMap(cardResponseDTOs -> ServerResponse.status(cardResponseDTOs.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(cardResponseDTOs));
    }

    private Mono<ServerResponse> updateCard(ServerRequest request) {
        return request.bodyToMono(CardRequestDTO.class)
                .flatMap(cardHandler::updateCard)
                .flatMap(cardResponseDTO -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(cardResponseDTO))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private Mono<ServerResponse> deleteCard(ServerRequest request) {
        return request.bodyToMono(CardRequestDTO.class)
                .flatMap(cardHandler::deleteCard)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }
}
