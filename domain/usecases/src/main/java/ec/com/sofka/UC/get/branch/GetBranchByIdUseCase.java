package ec.com.sofka.UC.get.branch;

import ec.com.sofka.Branch;
import ec.com.sofka.gateway.BranchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GetBranchByIdUseCase {
    private final BranchRepository repository;

    public GetBranchByIdUseCase(BranchRepository repository) {
        this.repository = repository;
    }

    public Mono<Branch> apply(int id) {
        return null;
    }
}
