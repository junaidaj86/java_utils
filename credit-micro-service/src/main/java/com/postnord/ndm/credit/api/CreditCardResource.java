package com.postnord.ndm.credit.api;

import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;
import com.postnord.ndm.credit.api.dto.CreditCardDto;
import com.postnord.ndm.credit.repository.CreditCardRepository;
import com.postnord.ndm.credit.kafkaManager.EventProducer;

import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static com.postnord.ndm.credit.util.ConstantsHelper.JSON_B;


@Path("/credit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SuppressWarnings({"PMD.ExcessiveImports", "PMD.AvoidDuplicateLiterals", "PMD.GuardLogStatement"})
public class CreditCardResource {

    @Inject
    EventProducer eventProducer;
    @Inject
    CreditCardRepository creditCardRepository;

    @POST
    public Uni<Response> createCredit(@Valid final CreditCardDto creditCardDto) {
    NdmLogger.info(LogRecord
                    .builder()
                    .message("Add is already called")
                    .category("credit-endpoint-generic")
                    .extraData(Map.of("credit payload: ", JSON_B.toJson(creditCardDto)))
                    .build());

        return eventProducer.produceEvent(creditCardDto);
    }

    @GET
    public Uni<Response> creditList() {
     NdmLogger.info(LogRecord
                    .builder()
                    .message("getAllData is already called")
                    .category("credit-endpoint-generic")
                    .build());

        return creditCardRepository
                .creditCardList()
                .onItem()
                .transform(item -> Response
                        .ok(item)
                        .build());
    }
}
