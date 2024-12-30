package ec.com.sofka.mapper;


import ec.com.sofka.Account;
import ec.com.sofka.data.AccountInDTO;
import ec.com.sofka.data.AccountOutDTO;

public class AccountMapper {

    public static Account toEntity(AccountInDTO accountInDTO) {
        if (accountInDTO == null) {
            return null;
        }

        Account account = new Account();
        account.setAccountNumber(accountInDTO.getAccountNumber());
        account.setBalance(accountInDTO.getBalance());
        account.setCustumerId(accountInDTO.getCustumerId());
        account.setCardId(accountInDTO.getCardId());

        return account;
    }

    public static AccountOutDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }

        AccountOutDTO accountOutDTO = new AccountOutDTO();
        accountOutDTO.setId(account.getId());
        accountOutDTO.setAccountNumber(account.getAccountNumber());
        accountOutDTO.setBalance(account.getBalance());
        accountOutDTO.setCustumerId(account.getCustumerId());

        return accountOutDTO;
    }

}
