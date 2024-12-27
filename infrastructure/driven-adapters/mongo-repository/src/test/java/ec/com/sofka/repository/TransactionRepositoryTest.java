package ec.com.sofka.repository;

import ec.com.sofka.document.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class)
@DataMongoTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testSaveAndFindById() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountId("12345");
        transaction.setAmount(new BigDecimal("100.0"));
        transaction.setDescription("Pago de factura");
        transaction.setTransactionType("crédito");
        transaction.setDate(LocalDate.now());

        Mono<TransactionEntity> saveMono = transactionRepository.save(transaction);

        StepVerifier.create(saveMono)
                .assertNext(savedTransaction -> {
                    assertThat(savedTransaction.getId()).isNotNull();
                    assertThat(savedTransaction.getAccountId()).isEqualTo("12345");
                    assertThat(savedTransaction.getAmount()).isEqualTo(new BigDecimal("100.0"));
                    assertThat(savedTransaction.getDescription()).isEqualTo("Pago de factura");
                    assertThat(savedTransaction.getTransactionType()).isEqualTo("crédito");
                    assertThat(savedTransaction.getDate()).isEqualTo(LocalDate.now());
                })
                .verifyComplete();

        Mono<TransactionEntity> findMono = transactionRepository.findById(transaction.getId());

        StepVerifier.create(findMono)
                .assertNext(foundTransaction -> {
                    assertThat(foundTransaction.getId()).isEqualTo(transaction.getId());
                    assertThat(foundTransaction.getAccountId()).isEqualTo("12345");
                    assertThat(foundTransaction.getAmount()).isEqualTo(new BigDecimal("100.0"));
                    assertThat(foundTransaction.getDescription()).isEqualTo("Pago de factura");
                    assertThat(foundTransaction.getTransactionType()).isEqualTo("crédito");
                    assertThat(foundTransaction.getDate()).isEqualTo(LocalDate.now());
                })
                .verifyComplete();
    }


    @Test
    public void testDeleteTransaction() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountId("12345");
        transaction.setAmount(new BigDecimal("100.0"));
        transaction.setDescription("Pago de factura");
        transaction.setTransactionType("crédito");
        transaction.setDate(LocalDate.now());

        Mono<TransactionEntity> saveMono = transactionRepository.save(transaction);
        TransactionEntity savedTransaction = saveMono.block();

        Mono<Void> deleteMono = transactionRepository.deleteById(savedTransaction.getId());

        StepVerifier.create(deleteMono)
                .verifyComplete();

        Mono<TransactionEntity> findMono = transactionRepository.findById(savedTransaction.getId());

        StepVerifier.create(findMono)
                .expectNextCount(0)
                .verifyComplete();
    }
}
