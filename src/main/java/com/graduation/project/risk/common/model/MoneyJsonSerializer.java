package com.graduation.project.risk.common.model;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class MoneyJsonSerializer extends JsonSerializer<Money> {
    public MoneyJsonSerializer() {
    }

    public void serialize(Money value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (value != null) {
            gen.writeNumber(Double.parseDouble(value.toString()));
        } else {
            gen.writeNumber(0);
        }

    }
}

