package ru.element.lab.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * OffsetDateTimeDeserializer.
 */
public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> implements Serializable {
    private final DateTimeFormatter formatter;

    public OffsetDateTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            return OffsetDateTime.parse(jsonParser.getText(), this.formatter);
        } else {
            throw new DateTimeException("Не удалось десериализовать дату-время");
        }
    }
}