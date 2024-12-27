package com.bank.config;

import com.bank.data.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IAccountMongoRepository extends ReactiveMongoRepository<AccountEntity, String> {}