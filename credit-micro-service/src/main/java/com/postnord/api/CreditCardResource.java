package com.postnord.api;


import com.postnord.api.dto.CreditCardDto;
import com.postnord.kafkaManager.EventProducer;
import com.postnord.model.repository.CreditCardRepository;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/credit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreditCardResource {

    @Inject
    EventProducer eventProducer;
    @Inject
    CreditCardRepository creditCardRepository;

    @POST
    public Uni<Response> createCredit(@Valid final CreditCardDto creditCardDto) {
        return eventProducer.produceEvent(creditCardDto);
    }

    @GET
    public Uni<Response> CreditList() {
        return creditCardRepository
                .creditCardList()
                .onItem()
                .transform(item -> Response
                        .ok(item)
                        .build());
    }
}
