package ec.com.sofka.mapper;


import ec.com.sofka.Account;
import ec.com.sofka.dto.AccountRequestDTO;
import ec.com.sofka.dto.AccountResponseDTO;

public class AccountMapper {
    public static AccountResponseDTO fromEntity(Account account){
        return new AccountResponseDTO(account.getId(), account.getAccountNumber(), account.getBalance(), account.getUserId());
    }

    public static Account toEntity(AccountRequestDTO accountRequestDTO){
        return new Account(accountRequestDTO.getUserId());
    }
}
