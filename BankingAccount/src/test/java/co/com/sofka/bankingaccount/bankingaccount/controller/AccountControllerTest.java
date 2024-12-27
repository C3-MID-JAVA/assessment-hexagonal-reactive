package co.com.sofka.bankingaccount.bankingaccount.controller;

import co.com.sofka.bankingaccount.bankingaccount.application.dto.request.CreateAccountRequestDTO;
import co.com.sofka.bankingaccount.bankingaccount.application.dto.request.TransactionRequestDTO;
import co.com.sofka.bankingaccount.bankingaccount.application.dto.response.AccountWithTransactionsDTO;
import co.com.sofka.bankingaccount.bankingaccount.application.dto.response.AccountResponseTransactionDTO;
import co.com.sofka.bankingaccount.bankingaccount.application.controllers.AccountController;
import co.com.sofka.bankingaccount.bankingaccount.domain.factory.TransactionFactory;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.AccountEntity;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import co.com.sofka.bankingaccount.bankingaccount.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {
    @Mock
    private AccountService accountService;

    @Mock
    private TransactionFactory transactionFactory;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        AccountController accountController = new AccountController(accountService, transactionFactory);
        this.webTestClient = WebTestClient.bindToController(accountController).build();
    }

    @Test
    void testCreateBankAccount() {
        // Datos simulados
        CreateAccountRequestDTO requestDTO = new CreateAccountRequestDTO();
        requestDTO.setBalance(BigDecimal.valueOf(500));
        requestDTO.setEntitledUserName("John Doe");

        AccountEntity savedAccount = new AccountEntity();
        savedAccount.setId(UUID.randomUUID().toString());
        savedAccount.setBalance(requestDTO.getBalance());
        savedAccount.setEntitledUser(requestDTO.getEntitledUserName());

        when(accountService.createAccount(requestDTO)).thenReturn(Mono.just(savedAccount));

        // Ejecutar y verificar
        webTestClient.post()
                .uri("/api/bankAccount")
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountEntity.class)
                .value(account -> {
                    assert account.getId().equals(savedAccount.getId());
                    assert account.getBalance().compareTo(savedAccount.getBalance()) == 0;
                    assert account.getEntitledUser().equals(savedAccount.getEntitledUser());
                });

        verify(accountService, times(1)).createAccount(requestDTO);
    }

    @Test
    void testExecuteTransaction() {
        // Datos simulados
        String accountId = UUID.randomUUID().toString();
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setId(accountId);
        requestDTO.setType("ATM_WITHDRAWAL");
        requestDTO.setAmount(BigDecimal.valueOf(200));

        AccountEntity mockAccount = new AccountEntity();
        mockAccount.setId(accountId);
        mockAccount.setBalance(BigDecimal.valueOf(1000));

        TransactionEntity mockTransaction = mock(TransactionEntity.class);
        AccountResponseTransactionDTO responseDTO = new AccountResponseTransactionDTO(accountId, BigDecimal.valueOf(800));

        when(accountService.getAccount(accountId)).thenReturn(Mono.just(mockAccount));
        when(transactionFactory.createTransaction("ATM_WITHDRAWAL", BigDecimal.valueOf(200), accountId))
                .thenReturn(mockTransaction);
        when(accountService.executeTransaction(accountId, mockTransaction))
                .thenReturn(Mono.just(BigDecimal.valueOf(800)));

        // Ejecutar y verificar
        webTestClient.post()
                .uri("/api/bankAccount/transaction")
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponseTransactionDTO.class)
                .value(response -> {
                    assert response.getId().equals(responseDTO.getId());
                    assert response.getBalance().compareTo(responseDTO.getBalance()) == 0;
                });

        verify(accountService, times(1)).getAccount(accountId);
        verify(accountService, times(1)).executeTransaction(eq(accountId), any(TransactionEntity.class));
    }

    @Test
    void testGetAccountWithTransactions() {
        // Datos simulados
        String accountId = UUID.randomUUID().toString();
        AccountWithTransactionsDTO responseDTO = new AccountWithTransactionsDTO(accountId, BigDecimal.valueOf(1000), List.of());

        when(accountService.getAccountWithTransaction(accountId)).thenReturn(Mono.just(responseDTO));

        // Ejecutar y verificar
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/bankAccount").queryParam("id", accountId).build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(AccountWithTransactionsDTO.class)
                .value(response -> {
                    assert response.getId().equals(responseDTO.getId());
                    assert response.getBalance().compareTo(responseDTO.getBalance()) == 0;
                });

        verify(accountService, times(1)).getAccountWithTransaction(accountId);
    }

    @Test
    void testGetAccountWithTransactions_NotFound() {
        // Datos simulados
        String accountId = UUID.randomUUID().toString();

        when(accountService.getAccountWithTransaction(accountId)).thenReturn(Mono.error(new RuntimeException("Not Found")));

        // Ejecutar y verificar
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/bankAccount").queryParam("id", accountId).build())
                .exchange()
                .expectStatus().isNotFound();

        verify(accountService, times(1)).getAccountWithTransaction(accountId);
    }
}
