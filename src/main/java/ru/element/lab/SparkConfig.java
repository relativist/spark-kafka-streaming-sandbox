package ru.element.lab;

import org.apache.spark.SparkConf;

/**
 * Some.
 */
public class SparkConfig {

    public static final String KAFKA_BOOTSTRAP = "kafka:9092";

    public SparkConf confForYarn() {
        SparkConf conf = new SparkConf()
            .setAppName("sandbox-sks")
            .set("spark.worker.all", "1")
            //.set("spark.kafka.bootstrap.servers", KAFKA_BOOTSTRAP)
            .set("spark.worker.current", "1")
            //.set("spark.hadoop.url", "hadoopname-sdp-02.gisoms-platform.dev2.pd15.foms.mtp:8020")
            //.set("spark.hadoop.fs.defaultFS", "hadoopname-sdp-02.gisoms-platform.dev2.pd15.foms.mtp:8020")
            // .set("fs.defaultFS", "hadoopname-sdp-02.gisoms-platform.dev2.pd15.foms.mtp:8020")
            .set("spark.cleaner.referenceTracking.cleanCheckpoints", "true")
            .set("spark.streaming.backpressure.initialRate", "4")
            .set("spark.streaming.kafka.maxRatePerPartition", "4")
            .set("spark.streaming.kafka.consumer.pollTimeoutMs", "1000")
            .set("spark.shuffle.useOldFetchProtocol", "true")
            .set("spark.yarn.maxAppAttempts", "5");

        conf.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem");
        conf.set("spark.hadoop.fs.s3a.path.style.access", "true");
        conf.set("spark.s3.endpoint", "https://s3.gos.sbercloud.dev/");
        conf.set("spark.s3.access_key", "7bb2e375b0b14c7d9c3152f49b5ee3c7");
        conf.set("spark.s3.secret_key", "6611af62323644f2a3653e02c65904e6");
        conf.set("spark.hadoop.fs.s3a.connection.timeout", "60000");
        conf.set("spark.hadoop.fs.s3a.connection.maximum", "1000");
        conf.set("spark.hadoop.database.url", "jdbc:postgresql://localhost:5432/emd2hdfs");
        conf.set("spark.hadoop.database.user", "postgres");
        conf.set("spark.hadoop.database.password", "postgres");

        return conf;
    }

    public SparkConf confLocal() {
        SparkConf conf = new SparkConf()
            .setAppName("sandbox-sks")
            .setMaster("local[2]")
            .set("spark.ssl.security.protocol", "PLAINTEXT")
            .set("spark.worker.all", "1")
            .set("spark.kafka.bootstrap.servers", KAFKA_BOOTSTRAP)
            .set("spark.worker.current", "1")
            .set("spark.hadoop.url", "hadoopname-sdp-02.gisoms-platform.dev2.pd15.foms.mtp:8020")
            .set("spark.hadoop.fs.defaultFS", "hadoopname-sdp-02.gisoms-platform.dev2.pd15.foms.mtp:8020")
            .set("fs.defaultFS", "hadoopname-sdp-02.gisoms-platform.dev2.pd15.foms.mtp:8020")
            .set("spark.cleaner.referenceTracking.cleanCheckpoints", "true")
            .set("spark.streaming.backpressure.initialRate", "4")
            .set("spark.streaming.kafka.maxRatePerPartition", "4")
            .set("spark.streaming.kafka.consumer.pollTimeoutMs", "1000")
            .set("spark.shuffle.useOldFetchProtocol", "true")
            .set("spark.yarn.maxAppAttempts", "5");

        conf.set("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem");
        conf.set("spark.hadoop.fs.s3a.path.style.access", "true");
        conf.set("spark.s3.endpoint", "https://s3.gos.sbercloud.dev/");
        conf.set("spark.s3.access_key", "7bb2e375b0b14c7d9c3152f49b5ee3c7");
        conf.set("spark.s3.secret_key", "6611af62323644f2a3653e02c65904e6");
        conf.set("spark.hadoop.fs.s3a.connection.timeout", "60000");
        conf.set("spark.hadoop.fs.s3a.connection.maximum", "1000");
        conf.set("spark.hadoop.database.url", "jdbc:postgresql://localhost:5432/emd2hdfs");
        conf.set("spark.hadoop.database.user", "postgres");
        conf.set("spark.hadoop.database.password", "postgres");

        return conf;
    }
}
