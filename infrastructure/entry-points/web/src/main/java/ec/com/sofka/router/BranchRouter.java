package ec.com.sofka.router;

import ec.com.sofka.data.request.BranchRequestDTO;
import ec.com.sofka.data.response.BranchResponseDTO;
import ec.com.sofka.handler.BranchHandler;
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
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Tag(name = "Branch Management", description = "Endpoints for managing branches")
@Configuration
public class BranchRouter {

    private final BranchHandler branchHandler;

    public BranchRouter(BranchHandler branchHandler) {
        this.branchHandler = branchHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/branches/create",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"branches"},
                            operationId = "createBranch",
                            summary = "Create a new branch",
                            description = "Add a new branch to the system",
                            requestBody = @RequestBody(
                                    description = "Branch details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BranchRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Branch created successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = BranchResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid input data")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/branches/id",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"branches"},
                            operationId = "getBranchById",
                            summary = "Retrieve a branch by ID",
                            description = "Get the details of a branch using its ID",
                            requestBody = @RequestBody(
                                    description = "Branch details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BranchRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Branch retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = BranchResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Branch not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/branches/name",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"branches"},
                            operationId = "getBranchByName",
                            summary = "Retrieve a branch by name",
                            description = "Get the details of a branch using its name",
                            requestBody = @RequestBody(
                                    description = "Branch details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BranchRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Branch retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = BranchResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Branch not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/branches",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            tags = {"branches"},
                            operationId = "getAllBranches",
                            summary = "Retrieve all branches",
                            description = "Get the list of all branches",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Branches retrieved successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = BranchResponseDTO[].class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "No branches found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/branches",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            tags = {"branches"},
                            operationId = "updateBranch",
                            summary = "Update a branch",
                            description = "Update an existing branch with new details",
                            requestBody = @RequestBody(
                                    description = "Updated branch details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BranchRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Branch updated successfully",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = BranchResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid input data"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Branch not found")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/branches/delete",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"branches"},
                            operationId = "deleteBranch",
                            summary = "Delete a branch",
                            description = "Delete a branch by its ID",
                            requestBody = @RequestBody(
                                    description = "Deleted branch details",
                                    required = true,
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BranchRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "Branch deleted successfully"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Branch not found")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> branchRoutes() {
        return route(RequestPredicates.POST("/branches/create"), this::createBranch)
                .andRoute(POST("/branches/id"), this::getBranchById)
                .andRoute(POST("/branches/name"), this::getBranchByName)
                .andRoute(GET("/branches"), this::getAllBranches)
                .andRoute(PUT("/branches"), this::updateBranch)
                .andRoute(POST("/branches/delete"), this::deleteBranch);
    }

    private Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchRequestDTO.class)
                .flatMap(branchHandler::createBranch)
                .flatMap(branchResponseDTO -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(branchResponseDTO));
    }

    public Mono<ServerResponse> getBranchById(ServerRequest request) {
        return request.bodyToMono(BranchRequestDTO.class)
                .flatMap(branchHandler::getBranchById)
                .flatMap(branchResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(branchResponseDTO));
    }

    public Mono<ServerResponse> getBranchByName(ServerRequest request) {
        return request.bodyToMono(BranchRequestDTO.class)
                .flatMap(branchHandler::getBranchByName)
                .flatMap(branchResponseDTO -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(branchResponseDTO));
    }

    public Mono<ServerResponse> getAllBranches(ServerRequest request) {
        return branchHandler.getAllBranches()
                .collectList()
                .flatMap(elements ->
                        elements.isEmpty() ?
                                ServerResponse.status(HttpStatus.NOT_FOUND).build()
                                : ServerResponse.status(HttpStatus.FOUND)
                                .contentType(MediaType.APPLICATION_JSON).bodyValue(elements));
    }

    private Mono<ServerResponse> updateBranch(ServerRequest request) {
        return request.bodyToMono(BranchRequestDTO.class)
                .flatMap(branchHandler::updateBranch)
                .flatMap(branchResponseDTO -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(branchResponseDTO));
    }

    private Mono<ServerResponse> deleteBranch(ServerRequest request) {
        return request.bodyToMono(BranchRequestDTO.class)
                .flatMap(branchHandler::deleteBranch)
                .then(ServerResponse.noContent().build());
    }

}