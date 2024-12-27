package ec.com.sofka;

import ec.com.sofka.config.ITransactionMongoRepository;
import ec.com.sofka.data.TransactionEntity;
import ec.com.sofka.mappers.TransactionMapper;
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

public class MongoAdapterTransactionTest {

    @Mock
    private ITransactionMongoRepository repository;

    @InjectMocks
    private MongoAdapterTransaction mongoAdapterTransaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findTransactionById_success() {
        TransactionEntity transactionEntity = new TransactionEntity("1", new BigDecimal("100.0"), "DEBIT", new BigDecimal("1.0"), "1");
        when(repository.findById(anyString())).thenReturn(Mono.just(transactionEntity));

        Mono<Transaction> result = mongoAdapterTransaction.findTransactionById("1");

        StepVerifier.create(result)
                .expectNext(TransactionMapper.toModel(transactionEntity))
                .verifyComplete();
    }

    @Test
    public void findTransactionById_notFound() {
        when(repository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Transaction> result = mongoAdapterTransaction.findTransactionById("1");

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    public void saveTransaction_success() {
        Transaction transaction = new Transaction("1", new BigDecimal("100.0"), "DEBIT", new BigDecimal("1.0"), "1");
        TransactionEntity transactionEntity = TransactionMapper.toDocument(transaction);
        when(repository.save(any(TransactionEntity.class))).thenReturn(Mono.just(transactionEntity));

        Mono<Transaction> result = mongoAdapterTransaction.saveTransaction(transaction);

        StepVerifier.create(result)
                .expectNext(TransactionMapper.toModel(transactionEntity))
                .verifyComplete();
    }
}