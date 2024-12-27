package ec.com.sofka;

import java.time.LocalDateTime;

public class Card {
    private int id;
    private String cardNumber;
    private String cardType;
    private LocalDateTime expirationDate;
    private String cvv;

    public Card(String cardNumber, String cardType, String cvv, LocalDateTime expirationDate, int id) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.id = id;
    }

    public Card() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
