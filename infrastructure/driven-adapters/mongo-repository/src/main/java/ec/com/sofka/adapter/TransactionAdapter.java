package ec.com.sofka.adapter;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionDocument;
import ec.com.sofka.gateway.TransactionRepository;
import ec.com.sofka.mapper.DocumentToModelMapper;
import ec.com.sofka.mapper.ModelToDocumentMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class TransactionAdapter implements TransactionRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public TransactionAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Transaction> createTransaction(Mono<Transaction> transaction) {
        return transaction.flatMap(transactionModel -> ModelToDocumentMapper.toTransaction.apply(Mono.just(transactionModel)))
                .flatMap(reactiveMongoTemplate::save)
                .flatMap(transactionDocument -> DocumentToModelMapper.toTransaction.apply(Mono.just(transactionDocument)));
    }

    @Override
    public Mono<Transaction> findTransactionById(Mono<Integer> id) {
        return id.flatMap(transactionId -> reactiveMongoTemplate.findById(transactionId, TransactionDocument.class))
                .flatMap(transactionDocument -> DocumentToModelMapper.toTransaction.apply(Mono.just(transactionDocument)));
    }

    @Override
    public Flux<Transaction> findAll() {
        return reactiveMongoTemplate.findAll(TransactionDocument.class)
                .flatMap(transactionDocument -> DocumentToModelMapper.toTransaction.apply(Mono.just(transactionDocument)));
    }

    @Override
    public Flux<Transaction> findTransactionsByDestinationAccountId(Mono<Integer> id) {
        return id.flatMapMany(branchId -> {
            Query query = new Query(Criteria.where("destinationAccount").is(branchId));
            return reactiveMongoTemplate.findOne(query, TransactionDocument.class)
                    .flatMap(transactionDocument -> DocumentToModelMapper
                            .toTransaction.apply(Mono.just(transactionDocument)));
        });
    }

    @Override
    public Flux<Transaction> findTransactionsBySourceAccountId(Mono<Integer> id) {
        return id.flatMapMany(branchId -> {
            Query query = new Query(Criteria.where("sourceAccount").is(branchId));
            return reactiveMongoTemplate.findOne(query, TransactionDocument.class)
                    .flatMap(transactionDocument -> DocumentToModelMapper
                            .toTransaction.apply(Mono.just(transactionDocument)));
        });
    }

    @Override
    public Flux<Transaction> findTransactionsByBranchId(Mono<Integer> id) {
        return id.flatMapMany(branchId -> {
            Query query = new Query(Criteria.where("branch").is(branchId));
            return reactiveMongoTemplate.findOne(query, TransactionDocument.class)
                    .flatMap(transactionDocument -> DocumentToModelMapper
                            .toTransaction.apply(Mono.just(transactionDocument)));
        });
    }

    @Override
    public Flux<Transaction> findTransactionsByDate(Mono<LocalDateTime> date) {
        return date.flatMapMany(localDateTime ->
                        reactiveMongoTemplate.find(Query.query(Criteria.where("date").is(localDateTime)),
                                TransactionDocument.class))
                .flatMap(transactionDocument -> DocumentToModelMapper.toTransaction.apply(Mono.just(transactionDocument)));
    }

    @Override
    public Flux<Transaction> findTransactionsByType(Mono<String> type) {
        return type.flatMapMany(strType ->
                        reactiveMongoTemplate.find(Query.query(Criteria.where("type").is(strType)),
                                TransactionDocument.class))
                .flatMap(transactionDocument -> DocumentToModelMapper.toTransaction.apply(Mono.just(transactionDocument)));
    }

    @Override
    public Mono<Void> deleteById(Mono<Integer> id) {
        return id.flatMap(transactionId -> reactiveMongoTemplate
                        .remove(Query.query(Criteria.where("_id").is(transactionId)), TransactionDocument.class))
                .then();
    }
}
