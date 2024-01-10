package ru.element.lab.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.kafka010.CanCommitOffsets;
import org.apache.spark.streaming.kafka010.HasOffsetRanges;
import org.apache.spark.streaming.kafka010.OffsetRange;
import ru.element.lab.dto.RawOmsAsString;
import ru.element.lab.oms.client.RawOms;
import ru.element.lab.service.SerializableFunction;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Some.
 */
@Slf4j
public class OmsService {
    public void processOms(JavaRDD<ConsumerRecord<UUID, RawOms>> rdd, JavaInputDStream<ConsumerRecord<UUID, RawOms>> subscription) {
        final JavaRDD<RawOmsAsString> rawOmsAsStringJavaRDD = convertToOriginalType(rdd);
        try {
            System.out.println(OffsetDateTime.now() + ". TICK " + rawOmsAsStringJavaRDD.count());
            if (rdd.count() > 0) {
                rawOmsAsStringJavaRDD.foreach(rawOmsAsString -> {
                    System.out.println("PROCESSED: " + rawOmsAsString.getId());
                    log.info("PROCESSED LOG: " + rawOmsAsString.getId());
                });
            }

            sendKafkaAcknowledge(subscription, rdd);
        } catch (Exception e) {
            log.error("On processOms: ", e);
            /*getJssc().stop(true);
            getJssc().close();*/
        }
    }

    protected void sendKafkaAcknowledge(JavaInputDStream<ConsumerRecord<UUID, RawOms>> subscription, JavaRDD<ConsumerRecord<UUID, RawOms>> rdd) {
        OffsetRange[] offsetRanges = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
        ((CanCommitOffsets) subscription.inputDStream()).commitAsync(offsetRanges, (map, e) -> {
            if (e != null) {
                log.error("On Ack oms:", e);
            }
        });
    }

    protected JavaRDD<RawOmsAsString> convertToOriginalType(JavaRDD<ConsumerRecord<UUID, RawOms>> rdd) {
        return rdd.map(record -> getClassAsStringOms().apply(record.value()));
    }


    private static SerializableFunction<RawOms, RawOmsAsString> getClassAsStringOms() {
        return rawOms -> RawOmsAsString.builder()
            .emdKind(rawOms.getEmdKind())
            .emdSource(rawOms.getSource() == null ? null : rawOms.getSource().name())
            .id(rawOms.getId())
            .blob(rawOms.getBlob())
            .fileProcessId(rawOms.getFileProcessId())
            .fileName(rawOms.getFileName())
            .loadProcessId(rawOms.getLoadProcessId())
            .createdTime(rawOms.getCreatedTime() == null ? null : rawOms.getCreatedTime().toString())
            .regionId(rawOms.getRegionId())
            .version(rawOms.getVersion())
            .bucket(rawOms.getBucket())
            .key(rawOms.getKey())
            .docCd(rawOms.getDocCd())
            .docNum(rawOms.getDocNum())
            .docDt(rawOms.getDocDt() == null ? null : rawOms.getDocDt().toString())
            .period(rawOms.getPeriod())
            .build();
    }
}
