package ec.com.sofka.accounts.gateway;

import ec.com.sofka.Transaction;

public interface ITransactionRepository {
    Transaction findByAccountId(String accountId);

}
