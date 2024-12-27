package ec.com.sofka.mapper;

import ec.com.sofka.*;
import ec.com.sofka.data.*;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DocumentToModelMapper {
    public static final Function<Mono<AccountDocument>, Mono<Account>> toAccount = account ->
            account.map(accountDocument -> {
                Account accountModel = new Account();
                BeanUtils.copyProperties(accountDocument, accountModel);
                return accountModel;
            });

    public static final Function<Mono<BranchDocument>, Mono<Branch>> toBranch = branch ->
            branch.map(branchDocument -> {
                Branch branchModel = new Branch();
                BeanUtils.copyProperties(branchDocument, branchModel);
                return branchModel;
            });

    public static final Function<Mono<CardDocument>, Mono<Card>> toCard = card ->
            card.map(cardDocument -> {
                Card cardModel = new Card();
                BeanUtils.copyProperties(cardDocument, cardModel);
                return cardModel;
            });

    public static final Function<Mono<CustomerDocument>, Mono<Customer>> toCustomer = customer ->
            customer.map(customerDocument -> {
                Customer customerModel = new Customer();
                BeanUtils.copyProperties(customerDocument, customerModel);
                return customerModel;
            });

    public static final Function<Mono<TransactionDocument>, Mono<Transaction>> toTransaction = transactionDTO ->
            transactionDTO.map(transactionDocument -> {
                Transaction transactionModel = new Transaction();
                BeanUtils.copyProperties(transactionDocument, transactionModel);
                return transactionModel;
            });
}
