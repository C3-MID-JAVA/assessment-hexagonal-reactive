package ec.com.sofka.adapter;

import ec.com.sofka.Branch;
import ec.com.sofka.data.BranchDocument;
import ec.com.sofka.gateway.BranchRepository;
import ec.com.sofka.mapper.DocumentToModelMapper;
import ec.com.sofka.mapper.ModelToDocumentMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BranchAdapter implements BranchRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public BranchAdapter(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Branch> createBranch(Mono<Branch> branch) {
        return branch.flatMap(branchModel -> ModelToDocumentMapper.toBranch.apply(Mono.just(branchModel)))
                .flatMap(reactiveMongoTemplate::save)
                .flatMap(branchDocument -> DocumentToModelMapper.toBranch.apply(Mono.just(branchDocument)));
    }

    @Override
    public Mono<Branch> findBranchById(Mono<Integer> id) {
        return id.flatMap(branchId -> reactiveMongoTemplate.findById(branchId, BranchDocument.class))
                .flatMap(branchDocument -> DocumentToModelMapper.toBranch.apply(Mono.just(branchDocument)));
    }

    @Override
    public Mono<Boolean> existsById(Mono<Integer> id) {
        return id.flatMap(branchId ->
                reactiveMongoTemplate
                        .exists(Query
                                .query(Criteria
                                        .where("_id")
                                        .is(branchId)),
                                BranchDocument.class));
    }

    @Override
    public Mono<Branch> findBranchByName(Mono<String> name) {
        return name.flatMap(branchName ->
                        reactiveMongoTemplate
                                .findOne(Query
                                        .query(Criteria
                                                .where("name")
                                                .is(branchName)),
                                        BranchDocument.class))
                .flatMap(branchDocument -> DocumentToModelMapper.toBranch.apply(Mono.just(branchDocument)));
    }

    @Override
    public Flux<Branch> findAll() {
        return reactiveMongoTemplate.findAll(BranchDocument.class)
                .flatMap(branchDocument -> DocumentToModelMapper.toBranch.apply(Mono.just(branchDocument)));
    }

    @Override
    public Mono<Void> deleteById(Mono<Integer> id) {
        return id.flatMap(branchId -> reactiveMongoTemplate
                        .remove(Query.query(Criteria.where("_id").is(branchId)), BranchDocument.class))
                .then();
    }
}
