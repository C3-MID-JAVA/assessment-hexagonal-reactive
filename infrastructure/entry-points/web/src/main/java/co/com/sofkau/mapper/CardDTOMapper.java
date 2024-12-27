package co.com.sofkau.mapper;

import co.com.sofkau.Card;
import co.com.sofkau.data.AccountDTO;
import co.com.sofkau.data.CardDTO;

public class CardDTOMapper {

    public static CardDTO toCardDTO(Card card) {
        return  new CardDTO(card.getCardName(), card.getCardNumber(), card.getCardType(), card.getCardStatus(),
                card.getCardExpiryDate(), card.getCardLimit(),
                card.getCardHolderName(), AccountDTOMapper.toAccountDTO(card.getAccount())
        );
    }

    public static Card toCard(CardDTO cardDTO) {
        if(cardDTO ==null) return null;

        return new Card(null, cardDTO.getCardName(), cardDTO.getCardNumber(), cardDTO.getCardType(), cardDTO.getCardStatus(),
                cardDTO.getCardExpiryDate(), null, cardDTO.getCardLimit(),
                cardDTO.getCardHolderName(), AccountDTOMapper.toAccount(cardDTO.getAccount())
        );
    }
}
