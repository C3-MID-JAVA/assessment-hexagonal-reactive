package ec.com.sofka;


import ec.com.sofka.config.IAccountMongoRepository;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.mappers.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MongoAdapterAccountTest {

    @Mock
    private IAccountMongoRepository repository;

    @InjectMocks
    private MongoAdapterAccount mongoAdapterAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findByAccountById_success() {
        AccountEntity accountEntity = new AccountEntity("1", new BigDecimal("1000.0"), "1234567890", "John Doe", null);
        when(repository.findById(anyString())).thenReturn(Mono.just(accountEntity));

        Mono<Account> result = mongoAdapterAccount.findByAccountById("1");

        StepVerifier.create(result)
                .expectNext(AccountMapper.toModel(accountEntity))
                .verifyComplete();
    }

    @Test
    public void findByAccountById_notFound() {
        when(repository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Account> result = mongoAdapterAccount.findByAccountById("1");

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void saveAccount_success() {
        Account account = new Account("1", new BigDecimal("1000.0"), "1234567890", "John Doe");
        AccountEntity accountEntity = AccountMapper.toDocument(account);
        when(repository.save(any(AccountEntity.class))).thenReturn(Mono.just(accountEntity));

        Mono<Account> result = mongoAdapterAccount.saveAccount(account);

        StepVerifier.create(result)
                .expectNext(AccountMapper.toModel(accountEntity))
                .verifyComplete();
    }
}