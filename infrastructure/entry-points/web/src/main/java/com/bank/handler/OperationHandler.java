package com.bank.handler;

import com.bank.Operation;
import com.bank.data.OperationRequestDTO;
import com.bank.data.OperationResponseDTO;
import com.bank.mapper.OperationMapper;
import com.bank.operation.CreateOperationUseCase;
import com.bank.operation.FindAllOperationUseCase;
import com.bank.operation.FindOperationByIdUseCase;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OperationHandler {
    private final FindAllOperationUseCase findAllOperationUseCase;

    private final FindOperationByIdUseCase findOperationByIdUseCase;

    private final CreateOperationUseCase createOperationUseCase;

    public OperationHandler(
            FindAllOperationUseCase findAllOperationUseCase,
            FindOperationByIdUseCase findOperationByIdUseCase,
            CreateOperationUseCase createOperationUseCase
    ) {
        this.findAllOperationUseCase = findAllOperationUseCase;
        this.findOperationByIdUseCase = findOperationByIdUseCase;
        this.createOperationUseCase = createOperationUseCase;
    }

    public Flux<OperationResponseDTO> findAllOperations() {
        return findAllOperationUseCase.apply().map(OperationMapper::toDTO);
    }

    public Mono<OperationResponseDTO> findOperationById(String id) {
        return findOperationByIdUseCase.apply(id).map(OperationMapper::toDTO);
    }

    public Mono<OperationResponseDTO> createOperation(OperationRequestDTO operationRequestDTO) {
        Operation op = OperationMapper.toEntity(operationRequestDTO);
        return createOperationUseCase.apply(op).map(OperationMapper::toDTO);
    }
}
