package com.bank.operation;

import com.bank.Operation;
import com.bank.gateway.IOperationRepository;
import reactor.core.publisher.Mono;

public class CreateOperationUseCase {
    private final IOperationRepository repository;

    public CreateOperationUseCase(IOperationRepository repository) {
        this.repository = repository;
    }

    public Mono<Operation> apply(Operation operation) {
        return repository.createOperation(operation);
    }
}
