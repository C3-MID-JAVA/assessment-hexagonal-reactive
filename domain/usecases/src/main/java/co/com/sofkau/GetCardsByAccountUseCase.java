package co.com.sofkau;

import co.com.sofkau.gateway.ICardRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Component
public class GetCardsByAccountUseCase {
    private final ICardRepository cardRepository;
    private final GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase;


    public GetCardsByAccountUseCase(ICardRepository cardRepository, GetAccountByAccountNumberUseCase getAccountByAccountNumberUseCase) {
        this.cardRepository = cardRepository;
        this.getAccountByAccountNumberUseCase = getAccountByAccountNumberUseCase;
    }

    public Flux<Card> apply(String accountId) {
        return getAccountByAccountNumberUseCase.apply(accountId)
                .flatMapMany(accountModel -> cardRepository.findByAccount_Id(accountModel.getId()));
    }
}
