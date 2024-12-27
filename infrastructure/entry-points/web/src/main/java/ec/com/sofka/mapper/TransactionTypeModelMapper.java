package ec.com.sofka.mapper;

import ec.com.sofka.TransactionType;
import ec.com.sofka.data.TransactionTypeRequestDTO;
import ec.com.sofka.data.TransactionTypeResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeModelMapper {

    public static TransactionType mapToModel(TransactionTypeRequestDTO transactionTypeRequestDTO) {
        if (transactionTypeRequestDTO == null) {
            return null;
        }
        TransactionType transactionType = new TransactionType();
        transactionType.setType(transactionTypeRequestDTO.getType());
        transactionType.setDescription(transactionTypeRequestDTO.getDescription());
        transactionType.setValue(transactionTypeRequestDTO.getValue());
        transactionType.setDiscount(transactionTypeRequestDTO.getDiscount());
        transactionType.setTransactionCost(transactionTypeRequestDTO.getTransactionCost());
        transactionType.setStatus(transactionTypeRequestDTO.getStatus());

        return transactionType;
    }

    public static TransactionTypeResponseDTO mapToDTO(TransactionType transactionType) {
        if (transactionType == null) {
            return null;
        }
        return new TransactionTypeResponseDTO(
                transactionType.getType(),
                transactionType.getDescription(),
                transactionType.getValue(),
                transactionType.getDiscount(),
                transactionType.getDiscount(),
                transactionType.getStatus()
        );
    }

}