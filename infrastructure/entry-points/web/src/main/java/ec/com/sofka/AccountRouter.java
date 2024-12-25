package ec.com.sofka;

import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import ec.com.sofka.handler.account.AccountHandler;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountRouter {

    private final AccountHandler handler;

    public AccountRouter(AccountHandler handler) {
        this.handler = handler;
    }


    @GetMapping("/{accountNumber}")
    public Mono<ResponseEntity<AccountResponseDTO>> getAccountByAccountNumber(@PathVariable String accountNumber) {
        return handler.getAccountByAccountNumber(accountNumber)
                .map(accountResponseDTO -> ResponseEntity.status(HttpStatus.OK).body(accountResponseDTO))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)));
    }

    @PostMapping
    public Mono<ResponseEntity<AccountResponseDTO>> createAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        return handler.createAccount(accountRequestDTO)
                .map(accountResponseDTO -> ResponseEntity.status(HttpStatus.CREATED).body(accountResponseDTO))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)));
    }
}
