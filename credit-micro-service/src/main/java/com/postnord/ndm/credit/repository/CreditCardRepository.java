package com.postnord.ndm.credit.repository;

import com.postnord.ndm.credit.api.dto.CreditCardDto;
import com.postnord.ndm.credit.repository.model.CreditCardEntity;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;


@ApplicationScoped
@SuppressWarnings("PMD.GuardLogStatement")
public class CreditCardRepository {

       @ReactiveTransactional
       public Uni<Void> addCreditCard(final CreditCardDto creditCardDto) {

           var creditCardEntity = new CreditCardEntity();
           creditCardEntity.availableCredit = creditCardDto.getAvailableCredit();
           creditCardEntity.cardNumber = creditCardDto.getCardNumber();
           creditCardEntity.ownerName = creditCardDto.getOwnerName();
           creditCardEntity.expirationMonth = creditCardDto.getExpirationMonth();
           creditCardEntity.expirationYear = creditCardDto.getExpirationYear();
           creditCardEntity.securityCode = creditCardDto.getSecurityCode();

           return creditCardEntity
                   .persistAndFlush()
                   .replaceWith(Uni
                           .createFrom()
                           .voidItem())
                   .onFailure()
                   .transform(t -> new IllegalStateException("Error"));


       }


       @ReactiveTransactional
       public Uni<List<CreditCardEntity>> creditCardList() {
           return CreditCardEntity.listAll();
       }
}
