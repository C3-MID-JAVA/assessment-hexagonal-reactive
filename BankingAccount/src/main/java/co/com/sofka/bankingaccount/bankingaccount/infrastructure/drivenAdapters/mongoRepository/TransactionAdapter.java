package co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository;

import co.com.sofka.bankingaccount.bankingaccount.domain.model.Transaction;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.TransactionRepository;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.config.ITransactionRepository;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.persistence.mapper.TransactionMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TransactionAdapter implements TransactionRepository {
    private final ITransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionAdapter(ITransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Mono<Transaction> createTransaction(Transaction transaction) {
        TransactionEntity entity = transactionMapper.toEntity(transaction);
        return transactionRepository.save(entity)
                .map(transactionMapper::toModel);
    }

    @Override
    public Mono<Transaction> getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transactionMapper::toModel);
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        return transactionRepository.findAll()
                .map(transactionMapper::toModel);
    }

    @Override
    public Flux<Transaction> getTransactionsByBankAccount(String bankAccountId) {
        return transactionRepository.findByAccountId(bankAccountId)
                .map(transactionMapper::toModel);
    }
}
