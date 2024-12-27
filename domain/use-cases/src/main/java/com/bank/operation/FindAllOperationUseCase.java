package com.bank.operation;

import com.bank.Operation;
import com.bank.gateway.IOperationRepository;
import reactor.core.publisher.Flux;

public class FindAllOperationUseCase {
    private final IOperationRepository repository;

    public FindAllOperationUseCase(IOperationRepository repository) {
        this.repository = repository;
    }

    public Flux<Operation> apply() {
        return repository.findAllOperations();
    }
}
