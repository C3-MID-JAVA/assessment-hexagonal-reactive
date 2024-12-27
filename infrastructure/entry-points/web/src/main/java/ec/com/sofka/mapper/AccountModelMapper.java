package ec.com.sofka.mapper;

import ec.com.sofka.Account;
import ec.com.sofka.data.AccountRequestDTO;
import ec.com.sofka.data.AccountResponseDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AccountModelMapper {

    public static Account mapToModel(AccountRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setNumber(dto.getNumber());
        account.setAvailableBalance(dto.getAvailableBalance());
        account.setRetainedBalance(dto.getRetainedBalance());
        account.setStatus(dto.getStatus());
        return account;
    }

    public static AccountResponseDTO mapToDTO(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountResponseDTO(
                account.getNumber(),
                account.getAvailableBalance(),
                account.getRetainedBalance(),
                account.getStatus(),
                account.getUser() != null ? Mono.just(UserModelMapper.mapToDTO(account.getUser())) : null
        );
    }

}