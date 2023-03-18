package com.postnord.kafkaManager.handler;

import com.postnord.api.dto.CreditCardDto;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class EventDeserializer extends ObjectMapperDeserializer<CreditCardDto> {
    public EventDeserializer() {
        super(CreditCardDto.class);
    }
}
