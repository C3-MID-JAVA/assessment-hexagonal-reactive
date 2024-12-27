package com.bank.handler;

import com.bank.Account;
import com.bank.account.CreateAccountUseCase;
import com.bank.account.FindAccountByIdUseCase;
import com.bank.account.FindAllAccountsUseCase;
import com.bank.data.AccountRequestDTO;
import com.bank.data.AccountResponseDTO;
import com.bank.mapper.AccountMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AccountHandler {
    private final FindAllAccountsUseCase findAllAccountsUseCase;

    private final FindAccountByIdUseCase findAccountByIdUseCase;

    private final CreateAccountUseCase createAccountUseCase;

    public AccountHandler(
            FindAllAccountsUseCase findAllAccountsUseCase,
            FindAccountByIdUseCase findAccountByIdUseCase,
            CreateAccountUseCase createAccountUseCase
    ) {
        this.findAllAccountsUseCase = findAllAccountsUseCase;
        this.findAccountByIdUseCase = findAccountByIdUseCase;
        this.createAccountUseCase = createAccountUseCase;
    }

    public Flux<AccountResponseDTO> findAllAccounts() {
        return findAllAccountsUseCase.apply().map(AccountMapper::toDTO);
    }

    public Mono<AccountResponseDTO> findAccountById(String id) {
        return findAccountByIdUseCase.apply(id).map(AccountMapper::toDTO);
    }

    public Mono<AccountResponseDTO> createAccount(AccountRequestDTO accountRequestDTO) {
        Account acc = AccountMapper.toEntity(accountRequestDTO);
        return createAccountUseCase.apply(acc).map(AccountMapper::toDTO);
    }
}
