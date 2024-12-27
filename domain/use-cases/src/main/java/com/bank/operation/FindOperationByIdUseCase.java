package com.bank.operation;

import com.bank.Operation;
import com.bank.gateway.IOperationRepository;
import reactor.core.publisher.Mono;

public class FindOperationByIdUseCase {
    private final IOperationRepository repository;

    public FindOperationByIdUseCase(IOperationRepository repository) {
        this.repository = repository;
    }

    public Mono<Operation> apply(String id) {
        return repository.findOperationById(id);
    }
}
