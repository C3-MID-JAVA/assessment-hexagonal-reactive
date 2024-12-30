package ec.com.sofka.handler;

import ec.com.sofka.Transaction;
import ec.com.sofka.data.TransactionInDTO;
import ec.com.sofka.data.TransactionOutDTO;
import ec.com.sofka.mapper.TransactionMapper;
import ec.com.sofka.usecase.customer.SaveCustomerUseCase;
import ec.com.sofka.usecase.transaction.SaveTransactionUseCase;
import ec.com.sofka.usecase.util.TransactionCost;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Component
@Validated
public class TransactionHandler {

    private final SaveTransactionUseCase saveTransactionUseCase;

    public TransactionHandler(SaveTransactionUseCase saveTransactionUseCase) {
        this.saveTransactionUseCase = saveTransactionUseCase;
    }

    public Mono<TransactionOutDTO> makeBranchDeposit(@Valid TransactionInDTO transactionInDTO) {
        return processTransaction(transactionInDTO, TransactionCost.DEPOSITO_SUCURSAL.getCosto(), true);
    }

    public Mono<TransactionOutDTO> makeATMDeposit(@Valid TransactionInDTO transactionInDTO) {
        return processTransaction(transactionInDTO, TransactionCost.DEPOSITO_CAJERO.getCosto(), true);
    }

    public Mono<TransactionOutDTO> makeDepositToAnotherAccount(@Valid TransactionInDTO transactionInDTO) {
        return processTransaction(transactionInDTO, TransactionCost.DEPOSITO_OTRA_CUENTA.getCosto(), true);
    }

    public Mono<TransactionOutDTO> makePhysicalPurchase(@Valid TransactionInDTO transactionInDTO) {
        return processTransaction(transactionInDTO, TransactionCost.COMPRA_FISICA.getCosto(), false);
    }

    public Mono<TransactionOutDTO> makeOnlinePurchase(@Valid TransactionInDTO transactionInDTO) {
        return processTransaction(transactionInDTO, TransactionCost.COMPRA_WEB.getCosto(), false);
    }

    public Mono<TransactionOutDTO> makeATMWithdrawal(@Valid TransactionInDTO transactionInDTO) {
        return processTransaction(transactionInDTO, TransactionCost.RETIRO_CAJERO.getCosto(), false);
    }

    private Mono<TransactionOutDTO> processTransaction(TransactionInDTO transactionInDTO, double transactionCost, boolean isDeposit) {
        Transaction transaction = TransactionMapper.toEntity(transactionInDTO);
        return saveTransactionUseCase.performTransaction(transaction, transactionCost, isDeposit)
                .map(TransactionMapper::toDTO);
    }
}
