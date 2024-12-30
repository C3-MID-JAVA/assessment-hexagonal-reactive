package ec.com.sofka.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cards")
public class CardEntity {

    @Id
    private String id;
    private String cardNumber;
    private String type;

    public CardEntity() {
    }

    public CardEntity(String id, String cardNumber, String type) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
