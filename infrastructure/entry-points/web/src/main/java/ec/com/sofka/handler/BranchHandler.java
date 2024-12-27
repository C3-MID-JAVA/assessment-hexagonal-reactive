package ec.com.sofka.handler;

import ec.com.sofka.UC.create.CreateBranchUseCase;
import ec.com.sofka.UC.delete.DeleteBranchUseCase;
import ec.com.sofka.UC.get.branch.GetAllBranchesUseCase;
import ec.com.sofka.UC.get.branch.GetBranchByIdUseCase;
import ec.com.sofka.UC.get.branch.GetBranchByNameUseCase;
import ec.com.sofka.UC.update.UpdateBranchUseCase;
import ec.com.sofka.customException.AlreadyExistsException;
import ec.com.sofka.customException.NotFoundException;
import ec.com.sofka.data.request.BranchRequestDTO;
import ec.com.sofka.data.response.BranchResponseDTO;
import ec.com.sofka.mapper.DTORequestMapper;
import ec.com.sofka.mapper.DTOResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BranchHandler {
    private final CreateBranchUseCase createBranchUseCase;
    private final GetBranchByIdUseCase getBranchByIdUseCase;
    private final GetBranchByNameUseCase getBranchByNameUseCase;
    private final GetAllBranchesUseCase getAllBranchesUseCase;
    private final UpdateBranchUseCase updateBranchUseCase;
    private final DeleteBranchUseCase deleteBranchUseCase;

    public BranchHandler(
            CreateBranchUseCase createBranchUseCase,
            GetBranchByIdUseCase getBranchByIdUseCase,
            GetBranchByNameUseCase getBranchByNameUseCase,
            GetAllBranchesUseCase getAllBranchesUseCase,
            UpdateBranchUseCase updateBranchUseCase,
            DeleteBranchUseCase deleteBranchUseCase
    ) {
        this.createBranchUseCase = createBranchUseCase;
        this.getBranchByIdUseCase = getBranchByIdUseCase;
        this.getBranchByNameUseCase = getBranchByNameUseCase;
        this.getAllBranchesUseCase = getAllBranchesUseCase;
        this.updateBranchUseCase = updateBranchUseCase;
        this.deleteBranchUseCase = deleteBranchUseCase;
    }

    public Mono<BranchResponseDTO> createBranch(BranchRequestDTO branchRequestDTO) {
        return DTOResponseMapper
                .toBranchResponseDTO
                .apply(createBranchUseCase
                        .apply(DTORequestMapper
                                .toBranch
                                .apply(Mono.just(branchRequestDTO))))
                .onErrorResume(e -> {
                    if (e instanceof AlreadyExistsException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage()));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<BranchResponseDTO> getBranchById(BranchRequestDTO branchRequestDTO) {
        return DTOResponseMapper
                .toBranchResponseDTO
                .apply(getBranchByIdUseCase
                        .apply(branchRequestDTO.getId()))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<BranchResponseDTO> getBranchByName(BranchRequestDTO branchRequestDTO) {
        return DTOResponseMapper
                .toBranchResponseDTO
                .apply(getBranchByNameUseCase
                        .apply(branchRequestDTO.getName()))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Flux<BranchResponseDTO> getAllBranches() {
        return getAllBranchesUseCase.apply()
                .flatMap(branch -> DTOResponseMapper.toBranchResponseDTO.apply(Mono.just(branch)))
                .onErrorResume(e -> {
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<BranchResponseDTO> updateBranch(BranchRequestDTO branchRequestDTO) {
        return DTOResponseMapper
                .toBranchResponseDTO
                .apply(updateBranchUseCase
                        .apply(DTORequestMapper.toBranch.apply(Mono.just(branchRequestDTO))))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<Void> deleteBranch(BranchRequestDTO branchRequestDTO) {
        return deleteBranchUseCase.apply(branchRequestDTO.getId())
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }
}
