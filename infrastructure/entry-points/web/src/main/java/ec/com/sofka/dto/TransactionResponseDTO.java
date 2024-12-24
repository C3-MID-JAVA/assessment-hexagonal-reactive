package ec.com.sofka.dto;

import ec.com.sofka.TransactionType;

import java.time.LocalDateTime;


public class TransactionResponseDTO {
    private String id;
    private double fee;
    private double netAmount;
    private TransactionType type;
    private LocalDateTime timestamp;

    public TransactionResponseDTO(String id, double fee, double netAmount, TransactionType type, LocalDateTime timestamp) {
        this.id = id;
        this.fee = fee;
        this.netAmount = netAmount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
