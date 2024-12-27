package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;

public class AccountDTOMapper {
    public static Account toEntity(AccountRequestDTO accountRequestDTO){
        Account account = new Account();
        account.setAccountHolder(accountRequestDTO.getAccountHolder());
        account.setAccountNumber(accountRequestDTO.getAccountNumber());
        account.setBalance(accountRequestDTO.getBalance());
        return account;
    }

    public static AccountResponseDTO fromEntity(Account account){
        return new AccountResponseDTO(account.getId(), account.getBalance(),
                account.getAccountNumber(),account.getAccountHolder(), account.getTransactions());
    }
}
