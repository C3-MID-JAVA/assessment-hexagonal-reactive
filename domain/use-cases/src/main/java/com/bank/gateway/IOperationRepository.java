package com.bank.gateway;

import com.bank.Operation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOperationRepository {
    Flux<Operation> findAllOperations();

    Mono<Operation> findOperationById(String id);

    Mono<Operation> createOperation(Operation operation);
}
