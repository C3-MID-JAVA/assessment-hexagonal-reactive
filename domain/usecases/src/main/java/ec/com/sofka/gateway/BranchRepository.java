package ec.com.sofka.gateway;

import ec.com.sofka.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {

    Mono<Branch> createBranch(Mono<Branch> branch);

    Mono<Branch> findBranchById(Mono<Integer> id);

    Mono<Boolean> existsById(Mono<Integer> id);

    Mono<Branch> findBranchByName(Mono<String> name);

    Flux<Branch> findAll();

    Mono<Void> deleteById(Mono<Integer> id);
}
