package co.com.sofkau;

import co.com.sofkau.gateway.ITransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTransactionUseCaseTest {

    @Mock
    ITransactionRepository transactionRepository;

    @Mock
    GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;

    @Mock
    GetCardByCardNumberUseCase getCardByCardNumberUseCase;

    @Mock
    UpdateAccountUseCase updateAccountUseCase;

    @InjectMocks
    CreateTransactionUseCase createTransactionUseCase;

    Transaction transaction;
    Card card;
    Account account;
    Account accountReceiver;

    @BeforeEach
    void setUp() {
        card = new Card();
        account = new Account();
        accountReceiver = new Account();

        card.setCardNumber("1234567890");
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000.00));
        accountReceiver.setAccountNumber("1234567890");
        accountReceiver.setBalance(BigDecimal.valueOf(1000.00));
    }

    @Test
    @DisplayName("AMT DEBIT")
    void apply_atmTransaction() {
        transaction = new AtmTransaction();
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setAmount(BigDecimal.valueOf(20.00));
        transaction.setDescription("description");
        transaction.setTransactionType("ATM");

        ((AtmTransaction) transaction).setAtmName("TEST");
        ((AtmTransaction) transaction).setOperationType("ATM_DEBIT");

        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.just(account));
        when(getCardByCardNumberUseCase.apply(card.getCardNumber())).thenReturn(Mono.just(card));
        //when(getAccountByAccountNumberUseCase.apply(accountReceiver.getAccountNumber())).thenReturn(Mono.just(accountReceiver));
        when(updateAccountUseCase.apply(account)).thenReturn(Mono.just(account));
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        StepVerifier.create(createTransactionUseCase.apply(transaction))
                .assertNext(transaction -> {
                    assertEquals("ATM", transaction.getTransactionType());
                    assertEquals(BigDecimal.valueOf(1), transaction.getTransactionFee());
                })
                .verifyComplete();


        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
        verify(getCardByCardNumberUseCase, times(1)).apply(card.getCardNumber());
        verify(updateAccountUseCase, times(1)).apply(account);
        verify(transactionRepository, times(1)).save(transaction);

    }

    @Test
    @DisplayName("AMT CREDIT")
    void apply_atmTransaction_ATM_CREDIT() {
        transaction = new AtmTransaction();
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setAmount(BigDecimal.valueOf(20.00));
        transaction.setDescription("description");
        transaction.setTransactionType("ATM");

        ((AtmTransaction) transaction).setAtmName("TEST");
        ((AtmTransaction) transaction).setOperationType("ATM_CREDIT");

        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.just(account));
        when(getCardByCardNumberUseCase.apply(card.getCardNumber())).thenReturn(Mono.just(card));
        //when(getAccountByAccountNumberUseCase.apply(accountReceiver.getAccountNumber())).thenReturn(Mono.just(accountReceiver));
        when(updateAccountUseCase.apply(account)).thenReturn(Mono.just(account));
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        StepVerifier.create(createTransactionUseCase.apply(transaction))
                .assertNext(transaction -> {
                    assertEquals("ATM", transaction.getTransactionType());
                    assertEquals(BigDecimal.valueOf(2), transaction.getTransactionFee());
                })
                .verifyComplete();


        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
        verify(getCardByCardNumberUseCase, times(1)).apply(card.getCardNumber());
        verify(updateAccountUseCase, times(1)).apply(account);
        verify(transactionRepository, times(1)).save(transaction);

    }

    @Test
    @DisplayName("BRANCH DEPOSIT")
    void apply_branchTransaction() {
        transaction = new BranchDeposit();
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setAmount(BigDecimal.valueOf(20.00));
        transaction.setDescription("description");
        transaction.setTransactionType("BD");

        ((BranchDeposit) transaction).setBranchName("BRANCH");

        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.just(account));
        when(getCardByCardNumberUseCase.apply(card.getCardNumber())).thenReturn(Mono.just(card));
        //when(getAccountByAccountNumberUseCase.apply(accountReceiver.getAccountNumber())).thenReturn(Mono.just(accountReceiver));
        when(updateAccountUseCase.apply(account)).thenReturn(Mono.just(account));
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        StepVerifier.create(createTransactionUseCase.apply(transaction))
                .assertNext(transaction -> {
                    assertEquals("BD", transaction.getTransactionType());
                    assertEquals(BigDecimal.valueOf(0), transaction.getTransactionFee());
                })
                .verifyComplete();


        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
        verify(getCardByCardNumberUseCase, times(1)).apply(card.getCardNumber());
        verify(updateAccountUseCase, times(1)).apply(account);
        verify(transactionRepository, times(1)).save(transaction);

    }

    @Test
    @DisplayName("PAYMENT STORE TRANSACTION")
    void apply_paymentStore() {
        transaction = new PaymentStoreTransaction();
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setAmount(BigDecimal.valueOf(20.00));
        transaction.setDescription("description");
        transaction.setTransactionType("SP");

        ((PaymentStoreTransaction) transaction).setMarketName("MARKET TEST");

        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.just(account));
        when(getCardByCardNumberUseCase.apply(card.getCardNumber())).thenReturn(Mono.just(card));
        //when(getAccountByAccountNumberUseCase.apply(accountReceiver.getAccountNumber())).thenReturn(Mono.just(accountReceiver));
        when(updateAccountUseCase.apply(account)).thenReturn(Mono.just(account));
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        StepVerifier.create(createTransactionUseCase.apply(transaction))
                .assertNext(transaction -> {
                    assertEquals("SP", transaction.getTransactionType());
                    assertEquals(BigDecimal.valueOf(0), transaction.getTransactionFee());
                })
                .verifyComplete();


        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
        verify(getCardByCardNumberUseCase, times(1)).apply(card.getCardNumber());
        verify(updateAccountUseCase, times(1)).apply(account);
        verify(transactionRepository, times(1)).save(transaction);

    }

    @Test
    @DisplayName("WEB PURCHASE")
    void apply_paymentWeb() {
        transaction = new PaymentWebTransaction();
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setAmount(BigDecimal.valueOf(20.00));
        transaction.setDescription("description");
        transaction.setTransactionType("WP");

        ((PaymentWebTransaction) transaction).setWebsite("www.tes.com");

        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.just(account));
        when(getCardByCardNumberUseCase.apply(card.getCardNumber())).thenReturn(Mono.just(card));
        //when(getAccountByAccountNumberUseCase.apply(accountReceiver.getAccountNumber())).thenReturn(Mono.just(accountReceiver));
        when(updateAccountUseCase.apply(account)).thenReturn(Mono.just(account));
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        StepVerifier.create(createTransactionUseCase.apply(transaction))
                .assertNext(transaction -> {
                    assertEquals("WP", transaction.getTransactionType());
                    assertEquals(BigDecimal.valueOf(0), transaction.getTransactionFee());
                })
                .verifyComplete();


        verify(getAccountByAccountNumberUseCase, times(1)).apply(account.getAccountNumber());
        verify(getCardByCardNumberUseCase, times(1)).apply(card.getCardNumber());
        verify(updateAccountUseCase, times(1)).apply(account);
        verify(transactionRepository, times(1)).save(transaction);

    }

    @Test
    @DisplayName("BETWEEN ACCOUNTS")
    void apply_accountDeposit() {
        transaction = new AccountDeposit();
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setAmount(BigDecimal.valueOf(20.00));
        transaction.setDescription("description");
        transaction.setTransactionType("BA");

        ((AccountDeposit) transaction).setAccountReceiver(accountReceiver);

        when(getAccountByAccountNumberUseCase.apply(account.getAccountNumber())).thenReturn(Mono.just(account));
        when(getAccountByAccountNumberUseCase.apply(accountReceiver.getAccountNumber())).thenReturn(Mono.just(accountReceiver));
        when(updateAccountUseCase.apply(accountReceiver)).thenReturn(Mono.just(accountReceiver));
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));

        StepVerifier.create(createTransactionUseCase.apply(transaction))
                .assertNext(transaction -> {
                    assertEquals("BA", transaction.getTransactionType());
                    assertEquals(BigDecimal.valueOf(1.5), transaction.getTransactionFee());
                })
                .verifyComplete();


        verify(getAccountByAccountNumberUseCase, times(2)).apply(account.getAccountNumber());
        verify(updateAccountUseCase, times(2)).apply(any());
        verify(transactionRepository, times(1)).save(transaction);

    }
}