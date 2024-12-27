package co.com.sofkau.mapper;

import co.com.sofkau.*;
import co.com.sofkau.data.*;

import java.util.Map;
import java.util.function.Function;

public class TransactionMapper {

    public static Transaction toTransaction(TransactionEntity transactionEntity) {
        Transaction transaction = null;
        switch (transactionEntity.getTransactionType()) {
            case ConstansTrType.BRANCH_DEPOSIT:
                transaction  = new BranchDeposit();
                BranchDepositEntity branchDepositEntity = (BranchDepositEntity) transactionEntity;
                ((BranchDeposit) transaction).setBranchName(branchDepositEntity.getBranchName());
                break;
            case ConstansTrType.WEB_PURCHASE:
                transaction = new PaymentWebTransaction();
                PaymentWebTransactionEntity paymentWebTransactionEntity = (PaymentWebTransactionEntity) transactionEntity;
                ((PaymentWebTransaction)  transaction).setWebsite(paymentWebTransactionEntity.getWebsite());
                break;
            case ConstansTrType.STORE_PURCHASE:
                transaction  = new PaymentStoreTransaction();
                PaymentStoreTransactionEntity paymentStoreTransaction = (PaymentStoreTransactionEntity) transactionEntity;
                ((PaymentStoreTransaction)  transaction).setMarketName(paymentStoreTransaction.getMarketName());
                break;
            case ConstansTrType.ATM:
                transaction  = new AtmTransaction();
                AtmTransactionEntity atmDeposit = (AtmTransactionEntity) transactionEntity;

                ((AtmTransaction) transaction).setAtmName(atmDeposit.getAtmName());
                ((AtmTransaction) transaction).setOperationType(atmDeposit.getOperationType());
                break;
            case ConstansTrType.BETWEEN_ACCOUNT:
                transaction  = new AccountDeposit();
                AccountDepositEntity accountDeposit = (AccountDepositEntity) transactionEntity;
                ((AccountDeposit) transaction).setAccountReceiver(AccountMapper.toAccount(accountDeposit.getAccountReceiver()));
                break;
        }
        transaction.setAmount(transactionEntity.getAmount());
        transaction.setDescription(transactionEntity.getDescription());
        transaction.setTransactionType(transactionEntity.getTransactionType());
        transaction.setTransactionFee(transactionEntity.getTransactionFee());
        transaction.setAccount(AccountMapper.toAccount(transactionEntity.getAccount()));
        transaction.setCard(CardMapper.toCard(transactionEntity.getCard()));
        transaction.setTransactionFee(transactionEntity.getTransactionFee());

        return transaction;
    }

    public static TransactionEntity toTransactionEntity(Transaction transaction) {

        return MAPPERS.getOrDefault(transaction.getClass(), defaultTransactionMapper())
                .apply(transaction);
    }

    private static final Map<Class<? extends Transaction>, Function<Transaction, TransactionEntity>> MAPPERS = Map.of(
            AtmTransaction.class, transaction -> {
                AtmTransaction atmTransaction = (AtmTransaction) transaction;
                return new AtmTransactionEntity(
                        null,
                        atmTransaction.getDescription(),
                        atmTransaction.getAmount(),
                        atmTransaction.getTransactionType(),
                        atmTransaction.getTransactionFee(),
                        null,
                        AccountMapper.toAccountEntity(atmTransaction.getAccount()),
                        CardMapper.toCardEntity(atmTransaction.getCard()),
                        atmTransaction.getAtmName(),
                        atmTransaction.getOperationType()
                );
            },
            AccountDeposit.class, transaction -> {
                AccountDeposit accountDeposit = (AccountDeposit) transaction;
                return new AccountDepositEntity(
                        null,
                        accountDeposit.getDescription(),
                        accountDeposit.getAmount(),
                        accountDeposit.getTransactionType(),
                        accountDeposit.getTransactionFee(),
                        null,
                        AccountMapper.toAccountEntity(accountDeposit.getAccount()),
                        CardMapper.toCardEntity(accountDeposit.getCard()),
                        AccountMapper.toAccountEntity(accountDeposit.getAccountReceiver())
                );
            },
            BranchDeposit.class, transaction -> {
                BranchDeposit branchDeposit = (BranchDeposit) transaction;
                return new BranchDepositEntity(
                        null,
                        branchDeposit.getDescription(),
                        branchDeposit.getAmount(),
                        branchDeposit.getTransactionType(),
                        branchDeposit.getTransactionFee(),
                        null,
                        AccountMapper.toAccountEntity(branchDeposit.getAccount()),
                        CardMapper.toCardEntity(branchDeposit.getCard()),
                        branchDeposit.getBranchName()
                );

            },
            PaymentWebTransaction.class, transaction -> {
                PaymentWebTransaction paymentWebTransaction = (PaymentWebTransaction) transaction;
                return new PaymentWebTransactionEntity(
                        null,
                        paymentWebTransaction.getDescription(),
                        paymentWebTransaction.getAmount(),
                        paymentWebTransaction.getTransactionType(),
                        paymentWebTransaction.getTransactionFee(),
                        null,
                        AccountMapper.toAccountEntity(paymentWebTransaction.getAccount()),
                        CardMapper.toCardEntity(paymentWebTransaction.getCard()),
                        paymentWebTransaction.getWebsite()
                );
            },
            PaymentStoreTransaction.class, transaction -> {
                PaymentStoreTransaction paymentStoreTransaction = (PaymentStoreTransaction) transaction;
                return new PaymentStoreTransactionEntity(
                        null,
                        paymentStoreTransaction.getDescription(),
                        paymentStoreTransaction.getAmount(),
                        paymentStoreTransaction.getTransactionType(),
                        paymentStoreTransaction.getTransactionFee(),
                        null,
                        AccountMapper.toAccountEntity(paymentStoreTransaction.getAccount()),
                        CardMapper.toCardEntity(paymentStoreTransaction.getCard()),
                        paymentStoreTransaction.getMarketName()
                );
            }
    );

    private static Function<Transaction, TransactionEntity> defaultTransactionMapper() {
        return transaction -> new TransactionEntity(
                null,
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTransactionFee(),
                null,
                AccountMapper.toAccountEntity(transaction.getAccount()),
                CardMapper.toCardEntity(transaction.getCard())
        );
    }
}
