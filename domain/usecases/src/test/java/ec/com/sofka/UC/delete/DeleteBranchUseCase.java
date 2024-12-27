package ec.com.sofka.UC.delete;

import ec.com.sofka.gateway.BranchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DeleteBranchUseCase {
    private final BranchRepository repository;

    public DeleteBranchUseCase(BranchRepository repository) {
        this.repository = repository;
    }

    public Mono<Void> apply(int id) {
        return null;
    }
}
