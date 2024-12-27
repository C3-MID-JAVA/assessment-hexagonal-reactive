package co.com.sofkau.mapper;

import co.com.sofkau.Account;
import co.com.sofkau.Card;
import co.com.sofkau.data.CardEntity;

public class CardMapper {

    public static Card toCard(CardEntity cardEntity) {
        if (cardEntity == null) return null;
        return new Card(cardEntity.getId(), cardEntity.getCardName(),cardEntity.getCardNumber(),
                cardEntity.getCardType(), cardEntity.getCardStatus(), cardEntity.getCardExpiryDate(),
                cardEntity.getCardCVV(), cardEntity.getCardLimit(), cardEntity.getCardHolderName(),
                AccountMapper.toAccount(cardEntity.getAccount()));
    }

    public static CardEntity toCardEntity(Card card) {
        if (card == null) return null;
        return new CardEntity(card.getId(), card.getCardName(),card.getCardNumber(),
                card.getCardType(), card.getCardStatus(), card.getCardExpiryDate(),
                card.getCardCVV(), card.getCardLimit(), card.getCardHolderName(),
                AccountMapper.toAccountEntity(card.getAccount()));
    }
}
