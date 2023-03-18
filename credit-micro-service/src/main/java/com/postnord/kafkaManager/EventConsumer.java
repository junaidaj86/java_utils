package com.postnord.kafkaManager;

import com.postnord.api.dto.CreditCardDto;
import com.postnord.model.repository.CreditCardRepository;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EventConsumer {
    @Inject
    CreditCardRepository creditCardRepository;

    @Incoming("event-out")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
    public Uni<Void> consumeEvent(final CreditCardDto creditCardDto) {
        Uni<Void> result = creditCardRepository.addCreditCard(creditCardDto);
        return result;
    }

}
