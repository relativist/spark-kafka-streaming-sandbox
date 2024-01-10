package ru.element.lab;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import ru.element.lab.oms.client.RawOms;
import ru.element.lab.service.KafkaParams;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * SOMe.
 */
@Slf4j
public class SandBoxApplication {

    public static void main(String[] args) {
        SandBoxApplication sandboxApplication = new SandBoxApplication();
        //SparkConf sparkConfig = new SparkConfig().confLocal();
        SparkConf sparkConfig = new SparkConfig().confForYarn();

        final JavaStreamingContext jsc = new JavaStreamingContext(sparkConfig, Durations.seconds(5));
        sandboxApplication.initOmsStreaming(jsc, sparkConfig);

        jsc.start();
        try {
            jsc.awaitTermination();
        } catch (InterruptedException e) {
            log.debug("Raw emd consumer finished : ", e);
        }
    }

    private void initOmsStreaming(JavaStreamingContext jsc, SparkConf sparkConf) {
        KafkaParams kafkaParams = new KafkaParams(sparkConf);
        final List<String> topics = Arrays.asList("raw-oms-big");
        JavaInputDStream<ConsumerRecord<UUID, RawOms>> subscription = KafkaUtils.createDirectStream(
            jsc,
            LocationStrategies.PreferConsistent(),
            ConsumerStrategies.Subscribe(
                topics,
                kafkaParams.getParamsOms())
        );

        System.out.println("start streaming...");
        //subscription.foreachRDD(rdd -> new OmsService().processOms(rdd, subscription));
        subscription.foreachRDD(rdd -> {
            final long count = rdd.count();
            System.out.println(OffsetDateTime.now() + " TICK " + count);
        });
    }


}