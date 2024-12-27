package ec.com.sofka.mapper;

import ec.com.sofka.TransactionType;
import ec.com.sofka.data.TransactionTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeEntityMapper {

    public static TransactionTypeEntity mapToEntity(TransactionType transactionType) {
        if (transactionType == null) {
            return null;
        }
        return new TransactionTypeEntity(
                transactionType.getId(),
                transactionType.getType(),
                transactionType.getDescription(),
                transactionType.getValue(),
                transactionType.getDiscount(),
                transactionType.getTransactionCost(),
                transactionType.getStatus());
    }

    public static TransactionType mapToModel(TransactionTypeEntity transactionTypeEntity) {
        if (transactionTypeEntity == null) {
            return null;
        }
        return new TransactionType(
                transactionTypeEntity.getId(),
                transactionTypeEntity.getType(),
                transactionTypeEntity.getDescription(),
                transactionTypeEntity.getValue(),
                transactionTypeEntity.getDiscount(),
                transactionTypeEntity.getDiscount(),
                transactionTypeEntity.getStatus()
        );
    }

}