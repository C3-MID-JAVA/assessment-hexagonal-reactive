package ec.com.sofka;

import ec.com.sofka.config.IMongoAccountRepository;
import ec.com.sofka.config.IMongoTransactionRepository;
import ec.com.sofka.data.AccountEntity;
import ec.com.sofka.data.TransactionsEntity;
import ec.com.sofka.gateway.AccountRepository;
import ec.com.sofka.gateway.TransactionRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MongoTransactionsAdapter implements TransactionRepository {

    private final IMongoTransactionRepository transactionRepository;
    private final IMongoAccountRepository accountRepository;

    public MongoTransactionsAdapter(IMongoTransactionRepository transactionRepository, IMongoAccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        Flux<TransactionsEntity> transactionsEntityFlux = transactionRepository.findAll();
        Flux<Transaction> transactions = transactionRepository.findAll()
                .map(this::mapToDomain);
        return transactionRepository.findAll()
                .map(this::mapToDomain);

    }

    @Override
    public Mono<Transaction> createTransaction(Transaction transaction) {
        return accountRepository.findById(transaction.getBankAccount().getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta bancaria no encontrada")))
                .flatMap(account -> transactionRepository.save(mapToEntity(transaction, account)))
                .map(this::mapToDomain)
                .onErrorMap(e -> new RuntimeException("Error al crear transacción: " + e.getMessage()));
    }



    private Transaction mapToDomain(TransactionsEntity entity) {
        Transaction transaction = new Transaction(
                entity.getId(),
                entity.getType(),
                entity.getAmount(),
                entity.getTransactionCost(),
                mapToDomainAccount(entity.getBankAccount()) // Mapea AccountEntity a Account
        );
        System.out.println("Mapped Transaction: " + transaction);
        return transaction;
    }

    private Account mapToDomainAccount(AccountEntity entity) {
        Account account = new Account(
                entity.getId(),
                entity.getBalance(),
                entity.getOwner()
        );
        System.out.println("Mapped Account: " + account);
        return account;
    }


    private TransactionsEntity mapToEntity(Transaction transaction,AccountEntity account) {
        TransactionsEntity entity = new TransactionsEntity(
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTransactionCost(),
                account // Mapea Account a AccountEntity
        );
        System.out.println("Mapped Transaction: " + entity);
        return entity;
    }

    private AccountEntity mapToEntityAccount(Account account) {
        return new AccountEntity(account.getBalance(), account.getOwner());
    }

}
