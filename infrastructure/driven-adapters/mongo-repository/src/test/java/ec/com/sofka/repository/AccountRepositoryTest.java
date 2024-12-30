package ec.com.sofka.repository;

import ec.com.sofka.document.AccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.math.BigDecimal;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class)
@DataMongoTest
public class AccountRepositoryTest {

    private IAccountRepository accountRepository;


    @Autowired
    public AccountRepositoryTest(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Test
    public void testSaveAndFindById() {
        AccountEntity account = new AccountEntity();
        account.setAccountNumber("123456");
        account.setBalance(new BigDecimal(500.0));
        account.setCustumerId("CUST001");
        account.setCardId("CARD001");

        Mono<AccountEntity> saveMono = accountRepository.save(account);

        StepVerifier.create(saveMono)
                .assertNext(savedAccount -> {
                    assertThat(savedAccount.getId()).isNotNull();
                    assertThat(savedAccount.getAccountNumber()).isEqualTo("123456");
                })
                .verifyComplete();

        Mono<AccountEntity> findMono = accountRepository.findById(account.getId());

        StepVerifier.create(findMono)
                .assertNext(foundAccount -> {
                    assertThat(foundAccount.getId()).isEqualTo(account.getId());
                    assertThat(foundAccount.getAccountNumber()).isEqualTo("123456");
                    assertThat(foundAccount.getBalance()).isEqualByComparingTo(new BigDecimal("500.00"));
                    assertThat(foundAccount.getCustumerId()).isEqualTo("CUST001");
                })
                .verifyComplete();
    }

    @Test
    public void testDelete() {
        AccountEntity account = new AccountEntity();
        account.setAccountNumber("123456");
        account.setBalance(new BigDecimal(500.0));
        account.setCustumerId("CUST001");
        account.setCardId("CARD001");

        Mono<Void> deleteMono = accountRepository.save(account)
                .flatMap(savedAccount -> accountRepository.deleteById(savedAccount.getId()));

        StepVerifier.create(deleteMono)
                .verifyComplete();

        StepVerifier.create(accountRepository.findById(account.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }
}
