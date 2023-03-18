package com.postnord.kafkaManager;

import com.postnord.api.dto.CreditCardDto;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Channel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class EventProducer {

    @Inject
    @Channel("event-out")
    @Broadcast
    MutinyEmitter<CreditCardDto> emitter;

    public Uni<Response> produceEvent(final CreditCardDto creditCardDto) {
        return emitter.send(creditCardDto)
                .map(entity -> Response
                        .ok(entity)
                        .build())
                .onFailure()
                .recoverWithItem(() -> Response
                        .status(500)
                        .build());
    }


}
