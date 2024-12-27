package ec.com.sofka.UC.create;

import ec.com.sofka.Branch;
import ec.com.sofka.gateway.BranchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateBranchUseCase {
    private final BranchRepository repository;

    public CreateBranchUseCase(BranchRepository repository) {
        this.repository = repository;
    }

    public Mono<Branch> apply(Mono<Branch> branch) {
        return repository.createBranch(branch);
    }
}
