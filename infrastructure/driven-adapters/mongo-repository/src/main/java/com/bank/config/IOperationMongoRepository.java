package com.bank.config;

import com.bank.data.OperationEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOperationMongoRepository extends ReactiveMongoRepository<OperationEntity, String> {}