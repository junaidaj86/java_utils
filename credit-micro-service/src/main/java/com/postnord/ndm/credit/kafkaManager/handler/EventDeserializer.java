package com.postnord.ndm.credit.kafkaManager.handler;

import com.postnord.ndm.credit.api.dto.CreditCardDto;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class EventDeserializer extends ObjectMapperDeserializer<CreditCardDto> {
    public EventDeserializer() {
        super(CreditCardDto.class);
    }
}
