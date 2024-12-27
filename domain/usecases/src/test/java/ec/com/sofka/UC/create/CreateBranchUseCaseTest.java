package ec.com.sofka.UC.create;

import ec.com.sofka.Branch;
import ec.com.sofka.gateway.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = CreateBranchUseCaseTest.class)
public class CreateBranchUseCaseTest {
    @Mock
    private BranchRepository repository;

    @InjectMocks
    private CreateBranchUseCase createBranchUseCase;

    private Branch testBranch;

    @BeforeEach
    void setUp() {
        testBranch = new Branch("123 Main St", 1, "Test Branch", "123-456-7890");
    }

    @Test
    void testApply_ShouldReturnBranch() {
        when(repository.createBranch(Mockito.any(Mono.class)))
                .thenReturn(Mono.just(testBranch));
        Mono<Branch> result = createBranchUseCase.apply(Mono.just(testBranch));
        StepVerifier.create(result)
                .expectNext(testBranch)
                .expectComplete()
                .verify();
        Mockito.verify(repository, Mockito.times(1)).createBranch(Mockito.any(Mono.class));
    }

    @Test
    void testApply_ShouldReturnError_WhenRepositoryFails() {
        when(repository.createBranch(Mockito.any(Mono.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving branch")));
        Mono<Branch> result = createBranchUseCase.apply(Mono.just(testBranch));
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
        verify(repository, times(1)).createBranch(Mockito.any(Mono.class));
    }
}
