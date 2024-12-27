package ec.com.sofka.mapper;

import ec.com.sofka.*;
import ec.com.sofka.data.*;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ModelToDocumentMapper {
    public static final Function<Mono<Account>, Mono<AccountDocument>> toAccount = account ->
            account.map(accountModel -> {
                AccountDocument accountDocument = new AccountDocument();
                BeanUtils.copyProperties(accountModel, accountDocument);
                return accountDocument;
            });

    public static final Function<Mono<Branch>, Mono<BranchDocument>> toBranch = branch ->
            branch.map(branchModel -> {
                BranchDocument branchDocument = new BranchDocument();
                BeanUtils.copyProperties(branchModel, branchDocument);
                return branchDocument;
            });

    public static final Function<Mono<Card>, Mono<CardDocument>> toCard = card ->
            card.map(cardModel -> {
                CardDocument cardDocument = new CardDocument();
                BeanUtils.copyProperties(cardModel, cardDocument);
                return cardDocument;
            });

    public static final Function<Mono<Customer>, Mono<CustomerDocument>> toCustomer = customer ->
            customer.map(customerModel -> {
                CustomerDocument customerDocument = new CustomerDocument();
                BeanUtils.copyProperties(customerModel, customerDocument);
                return customerDocument;
            });

    public static final Function<Mono<Transaction>, Mono<TransactionDocument>> toTransaction = transactionDTO ->
            transactionDTO.map(transactionModel -> {
                TransactionDocument transactionDocument = new TransactionDocument();
                BeanUtils.copyProperties(transactionModel, transactionDocument);
                return transactionDocument;
            });
}
