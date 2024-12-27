package co.com.sofka.bankingaccount.bankingaccount.domain.useCases;

import co.com.sofka.bankingaccount.bankingaccount.application.dto.TransactionDTO;
import co.com.sofka.bankingaccount.bankingaccount.domain.useCases.gateway.TransactionRepository;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.persistence.mapper.TransactionMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExecuteTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public ExecuteTransactionUseCase(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public Mono<TransactionDTO> execute(TransactionEntity transaction) {
        // Lógica de negocio: guardar la transacción
        return transactionRepository.createTransaction(transactionMapper.toModel(transaction))
                .map(savedTransaction -> new TransactionDTO(
                        savedTransaction.getId(),
                        savedTransaction.getType(),
                        savedTransaction.getAmount(),
                        savedTransaction.getCost()
                ));
    }
}