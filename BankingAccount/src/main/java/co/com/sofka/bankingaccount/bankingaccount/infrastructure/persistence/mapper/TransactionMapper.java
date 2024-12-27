package co.com.sofka.bankingaccount.bankingaccount.infrastructure.persistence.mapper;

import co.com.sofka.bankingaccount.bankingaccount.domain.factory.TransactionFactory;
import co.com.sofka.bankingaccount.bankingaccount.domain.model.*;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.TransactionEntity;
import co.com.sofka.bankingaccount.bankingaccount.infrastructure.drivenAdapters.mongoRepository.entities.transaction.impl.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TransactionFactory.class)
public abstract class TransactionMapper {
    // Convertir modelo a entidad
    public TransactionEntity toEntity(Transaction transaction) {
        if (transaction instanceof DepositFromAnotherAccount) {
            return new DepositFromAnotherAccountEntity(
                    transaction.getAmount(),
                    transaction.getAccountId()
            );
        } else if (transaction instanceof AtmDeposit) {
            return new AtmDepositEntity(
                    transaction.getAmount(),
                    transaction.getAccountId()
            );
        } else if (transaction instanceof AtmWithdrawal) {
            return new AtmWithdrawalEntity(
                    transaction.getAmount(),
                    transaction.getAccountId()
            );
        } else if (transaction instanceof BranchDeposit) {
            return new BranchDepositEntity(
                    transaction.getAmount(),
                    transaction.getAccountId()
            );
        } else if (transaction instanceof CardPhysicalBuy) {
            return new CardPhysicalBuyEntity(
                    transaction.getAmount(),
                    transaction.getAccountId()
            );
        } else if (transaction instanceof CardWebBuy) {
            return new CardWebBuyEntity(
                    transaction.getAmount(),
                    transaction.getAccountId()
            );
        }
        throw new IllegalArgumentException("Transaction type not supported");
    }

    // Convertir entidad a modelo
    public Transaction toModel(TransactionEntity entity) {
        switch (entity.getType()) {
            case "DEPOSIT_FROM_ANOTHER_ACCOUNT":
                return new DepositFromAnotherAccount(
                        entity.getId(),
                        entity.getAmount(),
                        entity.getAccountId()
                );
            case "ATM_DEPOSIT":
                return new AtmDeposit(
                        entity.getId(),
                        entity.getAmount(),
                        entity.getAccountId()
                );
            case "ATM_WITHDRAWAL":
                return new AtmWithdrawal(
                        entity.getId(),
                        entity.getAmount(),
                        entity.getAccountId()
                );
            case "BRANCH_DEPOSIT":
                return new BranchDeposit(
                        entity.getId(),
                        entity.getAmount(),
                        entity.getAccountId()
                );
            case "CARD_PHYSICAL_BUY":
                return new CardPhysicalBuy(
                        entity.getId(),
                        entity.getAmount(),
                        entity.getAccountId()
                );
            case "CARD_WEB_BUY":
                return new CardWebBuy(
                        entity.getId(),
                        entity.getAmount(),
                        entity.getAccountId()
                );
            default:
                throw new IllegalArgumentException("Transaction type not supported");
        }
    }
}
