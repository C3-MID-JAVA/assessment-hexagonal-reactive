package co.com.sofkau;

import co.com.sofkau.gateway.ITransactionRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class CreateTransactionUseCase {

    private final ITransactionRepository transactionRepository;
    private final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;
    private final GetCardByCardNumberUseCase getCardByCardNumberUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;


    public CreateTransactionUseCase(ITransactionRepository transactionRepository, GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase, GetCardByCardNumberUseCase getCardByCardNumberUseCase, UpdateAccountUseCase updateAccountUseCase) {
        this.transactionRepository = transactionRepository;
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;
        this.getCardByCardNumberUseCase = getCardByCardNumberUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
    }

    public Mono<Transaction> apply(Transaction transaction) {

        Function<Transaction, Mono<Account>> senderAccountFetcher = trans ->
                getAccountByAccountNumberUseCase.apply(trans.getAccount().getAccountNumber())
                        .switchIfEmpty(Mono.error(new RuntimeException("Sending account not found")));

        Function<Transaction, Mono<Account>> processDepositTransaction = trans2 ->

                getAccountByAccountNumberUseCase.apply(((AccountDeposit) trans2).getAccountReceiver().getAccountNumber())
                        .switchIfEmpty(Mono.error(new RuntimeException("Receiving account not found")))
                        .flatMap(receiverAcc -> senderAccountFetcher.apply(trans2)
                                .flatMap(senderAcc -> {
                                    transaction.processTransaction();
                                    receiverAcc.setBalance(receiverAcc.getBalance().add(transaction.getAmount()));
                                    senderAcc.setBalance(senderAcc.getBalance().subtract(transaction.getAmount()).subtract(transaction.getTransactionFee()));
                                    ((AccountDeposit) transaction).setAccountReceiver(receiverAcc);
                                    return updateAccountUseCase.apply(receiverAcc)
                                            .then(Mono.just(senderAcc));
                                }));


        Function<Transaction, Mono<Account>> processCardTransaction = trans3 ->
                getCardByCardNumberUseCase.apply(trans3.getCard().getCardNumber())
                        .switchIfEmpty(Mono.error(new RuntimeException("Card not found")))
                        .flatMap(card -> senderAccountFetcher.apply(transaction)
                                .flatMap(senderAC -> {
                                    transaction.processTransaction();
                                    senderAC.setBalance(senderAC.getBalance().subtract(transaction.getAmount()).subtract(transaction.getTransactionFee()));
                                    transaction.setCard(card);
                                    return Mono.just(senderAC);
                                }));

        if (transaction instanceof AccountDeposit) {
            return processDepositTransaction.apply(transaction)
                    .flatMap(sender -> {
                        transaction.setTimestamp(LocalDateTime.now());
                        transaction.setAccount(sender);
                        return updateAccountUseCase.apply(sender)
                                .then(transactionRepository.save(transaction));
                    });
        }

        return processCardTransaction.apply(transaction)
                .flatMap(sender -> {
                    transaction.setTimestamp(LocalDateTime.now());
                    transaction.setAccount(sender);
                    return updateAccountUseCase.apply(sender)
                            .then(transactionRepository.save(transaction));
                });

    }


}
