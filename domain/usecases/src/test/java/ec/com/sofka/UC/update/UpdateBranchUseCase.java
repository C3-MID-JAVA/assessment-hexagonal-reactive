package ec.com.sofka.UC.update;

import ec.com.sofka.Branch;
import ec.com.sofka.gateway.BranchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateBranchUseCase {
    private final BranchRepository repository;

    public UpdateBranchUseCase(BranchRepository repository) {
        this.repository = repository;
    }

    public Mono<Branch> apply(Mono<Branch> apply) {
        return null;
    }
}
