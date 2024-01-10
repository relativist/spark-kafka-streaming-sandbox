package ru.element.lab.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import ru.element.lab.oms.client.RawOms;

import java.io.IOException;
import java.io.Serializable;

/**
 * Десериализатор для типа сообщения из kafka.
 */
@Slf4j
public class SafeRawOmsDeserializer implements Deserializer<RawOms>, Serializable {
    private static final ObjectMapper objectMapper = Emd2HdfsUtils.objectMapper();

    @Override
    public RawOms deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, RawOms.class);
        } catch (IOException e) {
            log.error(String.format("Invalid message: %s", e.getMessage()));
            return new RawOms();
        }
    }
}