package ec.com.sofka.config;

import ec.com.sofka.ConfigTest;
import ec.com.sofka.data.AccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

@ActiveProfiles("test")
@ContextConfiguration(classes = ConfigTest.class)
@AutoConfigureDataMongo
@DataMongoTest
public class IAccountRepositoryTest {
    private final IAccountRepository repository;

    @Autowired
    public IAccountRepositoryTest(IAccountRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setUp() {
        repository.deleteAll().subscribe();
    }

    @Test
    public void TestSaveAndFindAccount() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setNumber("2200000000");
        accountEntity.setAvailableBalance(new BigDecimal(100));
        accountEntity.setRetainedBalance(new BigDecimal(0));
        accountEntity.setStatus("ACTIVE");

        repository.save(accountEntity).subscribe();

        StepVerifier.create(repository.findAll())
                .expectNextMatches(item -> item.getNumber().equals(accountEntity.getNumber()))
                .expectComplete()
                .verify();
    }

    @Test
    public void TestDeleteAccount() {
        Mono<Void> deleteResult = repository.deleteAll();

        StepVerifier.create(deleteResult)
                .expectComplete()
                .verify();

        StepVerifier.create(repository.findAll())
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}