package com.bank;

import com.bank.config.IOperationMongoRepository;
import com.bank.data.OperationEntity;
import com.bank.gateway.IOperationRepository;
import com.bank.mapper.OperationEntityMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class OperationAdapter implements IOperationRepository {

    private final IOperationMongoRepository repository;

    public OperationAdapter(IOperationMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Operation> findAllOperations() {
        return repository.findAllOperations().map(OperationEntityMapper::toOperation);
    }

    @Override
    public Mono<Operation> findOperationById(String id) {
        return repository.findOperationById(id).map(OperationEntityMapper::toOperation);
    }

    @Override
    public Mono<Operation> createOperation(Operation operation) {
        OperationEntity opEntity = OperationEntityMapper.toOperationEntity(operation);
        return repository.createOperation(opEntity).map(OperationEntityMapper::toOperation);
    }
}
