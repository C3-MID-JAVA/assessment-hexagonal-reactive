package ec.com.sofka.mapper;

import ec.com.sofka.dto.request.AccountRequestDTO;
import ec.com.sofka.Account;
import ec.com.sofka.dto.response.AccountResponseDTO;

public class AccountRequestMapper {
    public static Account mapToModel(AccountRequestDTO dto) {
        return Account.create(null, dto.getNumber(), dto.getInitialBalance());
    }

    public static AccountResponseDTO mapToDTO(Account account) {
        return new AccountResponseDTO(account.getId(), account.getAccountNumber(), account.getBalance().doubleValue());
    }
}