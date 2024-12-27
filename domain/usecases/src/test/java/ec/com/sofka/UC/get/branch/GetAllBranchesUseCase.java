package ec.com.sofka.UC.get.branch;

import ec.com.sofka.Branch;
import ec.com.sofka.gateway.BranchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class GetAllBranchesUseCase {
    private final BranchRepository repository;

    public GetAllBranchesUseCase(BranchRepository repository) {
        this.repository = repository;
    }

    public Flux<Branch> apply() {
        return null;
    }
}
