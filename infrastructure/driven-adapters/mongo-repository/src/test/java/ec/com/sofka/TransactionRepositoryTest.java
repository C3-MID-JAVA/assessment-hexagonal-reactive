package ec.com.sofka;

import ec.com.sofka.config.TransactionMongoRepository;
import ec.com.sofka.data.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@ContextConfiguration(classes = TestMongoConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionMongoRepository repository;

    @Test
    void testCrudOperations() {
        TransactionEntity transaction = new TransactionEntity("675dbabe03edcf54111957fe", TransactionType.BRANCH_DEPOSIT, BigDecimal.valueOf(2000), BigDecimal.valueOf(0.0),
                LocalDateTime.now(),"Depósito en sucursal", null);
        Mono<TransactionEntity> saveMono = repository.save(transaction);

        Mono<TransactionEntity> findMono = saveMono
                .then(repository.findById("675dbabe03edcf54111957fe"));

        Mono<TransactionEntity> updateMono = findMono.flatMap(foundTransaction -> {
            foundTransaction.setAmount(BigDecimal.valueOf(300));
            return repository.save(foundTransaction);
        });

        Mono<Void> deleteMono = updateMono.flatMap(updatedTransaction ->
                repository.deleteById(updatedTransaction.getId())
        );

        Flux<TransactionEntity> verifyDeletion = deleteMono
                .thenMany(repository.findAll());

        StepVerifier.create(saveMono)
                .expectNextMatches(savedTransaction -> savedTransaction.getId() != null)
                .verifyComplete();

        StepVerifier.create(findMono)
                .expectNextMatches(foundTransaction -> foundTransaction.getAmount().equals(BigDecimal.valueOf(2000))
                )
                .verifyComplete();

        StepVerifier.create(updateMono)
                .expectNextMatches(updatedTransaction -> updatedTransaction.getAmount().equals(BigDecimal.valueOf(300)))
                .verifyComplete();

        StepVerifier.create(verifyDeletion)
                .expectNextCount(0)
                .verifyComplete();
    }

}
