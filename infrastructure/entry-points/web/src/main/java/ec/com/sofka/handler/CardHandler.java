package ec.com.sofka.handler;

import ec.com.sofka.UC.create.CreateCardUseCase;
import ec.com.sofka.UC.delete.DeleteCardUseCase;
import ec.com.sofka.UC.get.card.*;
import ec.com.sofka.UC.update.UpdateCardUseCase;
import ec.com.sofka.customException.AlreadyExistsException;
import ec.com.sofka.customException.NotFoundException;
import ec.com.sofka.data.request.AccountRequestDTO;
import ec.com.sofka.data.request.CardRequestDTO;
import ec.com.sofka.data.response.CardResponseDTO;
import ec.com.sofka.mapper.DTORequestMapper;
import ec.com.sofka.mapper.DTOResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CardHandler {
    private final CreateCardUseCase createCardUseCase;
    private final GetCardByIdUseCase getCardByIdUseCase;
    private final GetCardByNumberUseCase getCardByNumberUseCase;
    private final GetAllCardsUseCase getAllCardsUseCase;
    private final GetCardsByAccountUseCase getCardsByAccountUseCase;
    private final GetCardsByTypeUseCase getCardsByTypeUseCase;
    private final UpdateCardUseCase updateCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;

    public CardHandler(
            CreateCardUseCase createCardUseCase,
            GetCardByIdUseCase getCardByIdUseCase,
            GetCardByNumberUseCase getCardByNumberUseCase,
            GetAllCardsUseCase getAllCardsUseCase,
            GetCardsByAccountUseCase getCardsByAccountUseCase,
            GetCardsByTypeUseCase getCardsByTypeUseCase,
            UpdateCardUseCase updateCardUseCase,
            DeleteCardUseCase deleteCardUseCase
    ) {
        this.createCardUseCase = createCardUseCase;
        this.getCardByIdUseCase = getCardByIdUseCase;
        this.getCardByNumberUseCase = getCardByNumberUseCase;
        this.getAllCardsUseCase = getAllCardsUseCase;
        this.getCardsByAccountUseCase = getCardsByAccountUseCase;
        this.getCardsByTypeUseCase = getCardsByTypeUseCase;
        this.updateCardUseCase = updateCardUseCase;
        this.deleteCardUseCase = deleteCardUseCase;
    }

    public Mono<CardResponseDTO> createCard(CardRequestDTO cardRequestDTO) {
        return DTOResponseMapper
                .toCardResponseDTO
                .apply(createCardUseCase
                        .apply(DTORequestMapper
                                .toCard
                                .apply(Mono.just(cardRequestDTO))))
                .onErrorResume(e -> {
                    if (e instanceof AlreadyExistsException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Card already exists"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<CardResponseDTO> getCardById(CardRequestDTO cardRequestDTO) {
        return DTOResponseMapper
                .toCardResponseDTO
                .apply(getCardByIdUseCase
                        .apply(cardRequestDTO.getId()))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<CardResponseDTO> getCardByNumber(CardRequestDTO cardRequestDTO) {
        return DTOResponseMapper
                .toCardResponseDTO
                .apply(getCardByNumberUseCase
                        .apply(cardRequestDTO.getCardNumber()))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Flux<CardResponseDTO> getAllCards() {
        return getAllCardsUseCase.apply()
                .flatMap(card -> DTOResponseMapper.toCardResponseDTO.apply(Mono.just(card)))
                .onErrorResume(e ->
                        Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching all cards")));
    }

    public Flux<CardResponseDTO> getCardsByAccountId(AccountRequestDTO accountRequestDTO) {
        return getCardsByAccountUseCase
                .apply(accountRequestDTO.getId())
                .flatMap(card -> DTOResponseMapper
                        .toCardResponseDTO
                        .apply(Mono.just(card)))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching cards by account")));
    }

    public Flux<CardResponseDTO> getCardsByType(CardRequestDTO cardRequestDTO) {
        return getCardsByTypeUseCase
                .apply(cardRequestDTO.getCardType())
                .flatMap(card -> DTOResponseMapper
                        .toCardResponseDTO
                        .apply(Mono.just(card)))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching cards by type")));
    }

    public Mono<CardResponseDTO> updateCard(CardRequestDTO cardRequestDTO) {
        return DTOResponseMapper
                .toCardResponseDTO
                .apply(updateCardUseCase
                        .apply(DTORequestMapper
                                .toCard
                                .apply(Mono.just(cardRequestDTO))))
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

    public Mono<Void> deleteCard(CardRequestDTO cardRequestDTO) {
        return deleteCardUseCase.apply(cardRequestDTO.getId())
                .onErrorResume(e -> {
                    if (e instanceof NotFoundException) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found"));
                    }
                    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                });
    }

}
