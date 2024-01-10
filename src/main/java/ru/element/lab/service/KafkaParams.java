package ru.element.lab.service;

import com.amazonaws.SDKGlobalConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.apache.spark.SparkConf;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.element.lab.oms.client.RawOms;
import ru.element.lab.utils.SafeRawOmsDeserializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.common.security.auth.SecurityProtocol.SSL;
import static org.springframework.kafka.support.serializer.JsonDeserializer.REMOVE_TYPE_INFO_HEADERS;
import static org.springframework.kafka.support.serializer.JsonDeserializer.USE_TYPE_INFO_HEADERS;

/**
 * Параметры подключения кафки.
 */
@Slf4j
@Setter
@Getter
public class KafkaParams {

    public static final String GROUP_ID = "group-sb-1";
    private final SparkConf sparkConf;

    public KafkaParams(SparkConf sparkConf) {
        this.sparkConf = sparkConf;
    }

    /**
     * Получить параметры конфигурации Apache Kafka для счетов по ОМС.
     *
     * @return мапа с парамерами.
     */
    public Map<String, Object> getParamsOms() {
        Map<String, Object> params = new HashMap<>();
        setDefaultParams(params);
        final String completeGroupId = String.format("%s-%s", "sndbox-oms", GROUP_ID);
        params.put("group.id", completeGroupId);
        params.put(JsonDeserializer.VALUE_DEFAULT_TYPE, RawOms.class.getCanonicalName());
        params.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SafeRawOmsDeserializer.class);
        //params.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        log.info(String.format("Use kafka oms group.id: %s", completeGroupId));
        return params;
    }


    private void setDefaultParams(Map<String, Object> params) {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true");

        final String bs = sparkConf.get("spark.kafka.bootstrap.servers");
        final String ssl = sparkConf.get("spark.ssl.security.protocol");
        final String tsl = sparkConf.get("spark.ssl.truststore.location");
        final String tsp = sparkConf.get("spark.ssl.truststore.password");
        final String ksl = sparkConf.get("spark.ssl.keystore.location");
        final String ksp = sparkConf.get("spark.ssl.keystore.password");
        System.out.println("bs: " + bs);
        System.out.println("ssl: " + ssl);
        System.out.println("tsl: " + tsl);
        System.out.println("tsp: " + tsp);
        System.out.println("ksl: " + ksl);
        System.out.println("ksp: " + ksp);

        params.put("bootstrap.servers", bs);
        params.put("security.protocol", ssl);
        if (SSL.name.equals(ssl)) {
            params.put("ssl.truststore.location", tsl);
            params.put("ssl.truststore.password", tsp);
            params.put("ssl.keystore.location", ksl);
            params.put("ssl.keystore.password", ksp);
        }
        params.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        params.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        params.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        params.put(ProducerConfig.VALUE_SERIALIZER_CLASS_DOC, JsonSerializer.class);
        params.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        params.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        params.put("spring.json.trusted.packages", "ru.element.lab.*");
        params.put("group.id", String.format("%s-%s", "sndbx", GROUP_ID));
        params.put(USE_TYPE_INFO_HEADERS, "false");
        params.put(REMOVE_TYPE_INFO_HEADERS, "true");
    }
}
