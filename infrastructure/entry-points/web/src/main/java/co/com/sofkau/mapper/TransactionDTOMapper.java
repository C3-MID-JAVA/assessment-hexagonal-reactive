package co.com.sofkau.mapper;

import co.com.sofkau.*;
import co.com.sofkau.data.TransactionDTO;

public class TransactionDTOMapper {
    public static TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO transactionDto = new TransactionDTO(transaction.getDescription(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTransactionFee(),
                null,
                null);


        if(transaction instanceof AtmTransaction atmTransaction) {
            transactionDto.setAtmName(atmTransaction.getAtmName());
            transactionDto.setOperationType(atmTransaction.getOperationType());
        }

        if(transaction instanceof AccountDeposit accountDeposit) {
            transactionDto.setAccountReceiver(AccountDTOMapper.toAccountDTO(accountDeposit.getAccountReceiver()));
        }
        if(transaction instanceof BranchDeposit branchTransaction) {
            transactionDto.setBranchName(branchTransaction.getBranchName());
        }
        if(transaction instanceof PaymentStoreTransaction paymentStoreTransaction) {
            transactionDto.setMarketName(paymentStoreTransaction.getMarketName());
        }

        if(transaction instanceof PaymentWebTransaction paymentStoreTransaction) {
            transactionDto.setWebsite(paymentStoreTransaction.getWebsite());
        }
        return transactionDto;

    }

    public static Transaction toTransaction(TransactionDTO transactionDTO) {
        if(transactionDTO == null) return null;
        Transaction transaction = null;
        switch (transactionDTO.getTransactionType()) {
            case ConstansTrType.BRANCH_DEPOSIT:
                transaction  = new BranchDeposit();
                ((BranchDeposit) transaction).setBranchName(transactionDTO.getBranchName());
                break;
            case ConstansTrType.STORE_PURCHASE:
                transaction  = new PaymentStoreTransaction();
                ((PaymentStoreTransaction)  transaction).setMarketName(transactionDTO.getMarketName());
                break;
            case ConstansTrType.WEB_PURCHASE:
                transaction  = new PaymentWebTransaction();
                ((PaymentWebTransaction)  transaction).setWebsite(transactionDTO.getWebsite());
                break;
            case ConstansTrType.ATM:
                transaction  = new AtmTransaction();

                ((AtmTransaction) transaction).setAtmName(transactionDTO.getAtmName());
                ((AtmTransaction) transaction).setOperationType(transactionDTO.getOperationType());
                break;
            case ConstansTrType.BETWEEN_ACCOUNT:
                transaction  = new AccountDeposit();
                ((AccountDeposit) transaction).setAccountReceiver(AccountDTOMapper.toAccount(transactionDTO.getAccountReceiver()));
                break;
        }
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setCard(CardDTOMapper.toCard(transactionDTO.getCard()));
        transaction.setTransactionFee(transactionDTO.getTransactionFee());
        transaction.setAccount(AccountDTOMapper.toAccount(transactionDTO.getAccount()));

        return transaction;

    }
}
