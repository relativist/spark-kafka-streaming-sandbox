spark-submit --deploy-mode cluster \
--master spark://spark:7077 \
--total-executor-cores 1 \
--class ru.element.lab.sandboxApplication \
--driver-memory 1G \
--executor-memory 1G \
--conf spark.kafka.bootstrap.servers=192.168.2.5:9092 \
--properties-file=/opt/bitnami/spark/apps/spark-1.properties \
/opt/bitnami/spark/apps/sandbox-kafka-streaming.jar


spark-submit --deploy-mode cluster \
--master yarn \
--total-executor-cores 1 \
--class ru.element.lab.sandboxApplication \
--driver-memory 1G \
--executor-memory 1G \
--conf spark.ssl.truststore.location=cmp-dev2-kafka-all.truststore.jks \
--conf spark.ssl.truststore.password=dm4zOKvUHZT7 \
--conf spark.ssl.keystore.location=cmp-dev2-kafka-all.keystore.jks \
--conf spark.ssl.keystore.password=dm4zOKvUHZT7 \
--conf spark.ssl.security.protocol=SSL \
--conf spark.kafka.bootstrap.servers=kafka-cmp-01.gisoms-customer.dev2.pd15.foms.mtp:9093,kafka-cmp-02.gisoms-customer.dev2.pd15.foms.mtp:9093,kafka-cmp-03.gisoms-customer.dev2.pd15.foms.mtp:9093 \
--properties-file=/opt/bitnami/spark/apps/spark-1.properties \
/opt/bitnami/spark/apps/sandbox-kafka-streaming.jar


# works
spark-submit --deploy-mode client \
--total-executor-cores 1 \
--class ru.element.lab.sandboxApplication \
--executor-memory 1G \
--properties-file=/opt/bitnami/spark/apps/spark-1.properties \
/opt/bitnami/spark/apps/sandbox-kafka-streaming.jar

# works
spark-submit --deploy-mode cluster \
--master yarn \
--total-executor-cores 1 \
--class org.apache.spark.examples.SparkPi \
--executor-memory 1G \
--properties-file=/opt/spark/user_files/spark-1.properties \
/opt/spark/user_files/spark-examples_2.12-3.3.0.jar


spark-submit --deploy-mode cluster \
--name sandbox-kafka-streaming \
--master yarn \
--total-executor-cores 1 \
--class ru.element.lab.sandboxApplication \
--driver-memory 1G \
--executor-memory 1G \
--conf spark.kafka.bootstrap.servers=192.168.2.5:9092 \
--conf spark.ssl.security.protocol=PLAINTEXT \
--files '/opt/spark/user_files/cmp-dev2-kafka-all.truststore.jks,/opt/spark/user_files/cmp-dev2-kafka-all.keystore.jks,/opt/spark/user_files/log4j.xml' \
--properties-file=/opt/spark/user_files/spark-1.properties \
--conf spark.ssl.truststore.location=cmp-dev2-kafka-all.truststore.jks \
--conf spark.ssl.truststore.password=dm4zOKvUHZT7 \
--conf spark.ssl.keystore.location=cmp-dev2-kafka-all.keystore.jks \
--conf spark.ssl.keystore.password=dm4zOKvUHZT7 \
--conf spark.yarn.submit.waitAppCompletion=false \
--conf spark.hadoop.url=hadoopname-sdp-01.gisoms-platform.dev2.pd15.foms.mtp:8020 \
--class ru.element.lab.sandboxApplication \
--executor-cores 1 \
--conf spark.serializer=org.apache.spark.serializer.KryoSerializer \
--driver-class-path /opt/spark/user_files/jars \
--jars '/opt/spark/user_files/jars/postgresql-42.4.2.jar,/opt/spark/user_files/jars/spark-sql_2.12-3.0.1.jar,/opt/spark/user_files/jars/spark-sql-kafka-0-10_2.12-3.0.1.jar,/opt/spark/user_files/jars/spark-streaming_2.12-3.0.1.jar,/opt/spark/user_files/jars/spark-streaming-kafka-0-10_2.12-3.0.1.jar,/opt/spark/user_files/jars/spark-token-provider-kafka-0-10_2.12-3.0.1.jar,/opt/spark/user_files/jars/kafka-clients-3.1.1.jar,/opt/spark/user_files/jars/oms-api-pd15-0.7.1-SNAPSHOT.jar,/opt/spark/user_files/jars/semd-api-0.1.0-SNAPSHOT.jar,/opt/spark/user_files/jars/jackson-datatype-jsr310-2.15.0.jar,/opt/spark/user_files/jars/gson-2.9.1.jar,/opt/spark/user_files/jars/greenplum-connector-apache-spark-scala_2.12-2.1.4.jar' \
user_files/sandbox-kafka-streaming.jar

# PD15
spark-submit \
--name sandbox-kafka-streaming \
--files '/opt/spark/user_files/cmp-dev2-kafka-all.truststore.jks,/opt/spark/user_files/cmp-dev2-kafka-all.keystore.jks,/opt/spark/user_files/log4j.xml' \
--properties-file=/opt/spark/user_files/spark-1.properties \
--conf spark.ssl.truststore.location=cmp-dev2-kafka-all.truststore.jks \
--conf spark.ssl.truststore.password=dm4zOKvUHZT7 \
--conf spark.ssl.keystore.location=cmp-dev2-kafka-all.keystore.jks \
--conf spark.ssl.keystore.password=dm4zOKvUHZT7 \
--conf spark.ssl.security.protocol=SSL \
--conf spark.kafka.bootstrap.servers=kafka-cmp-01.gisoms-customer.dev2.pd15.foms.mtp:9093 \
--conf spark.yarn.submit.waitAppCompletion=false \
--conf spark.hadoop.url=hadoopname-sdp-01.gisoms-platform.dev2.pd15.foms.mtp:8020 \
--class ru.element.lab.sandboxApplication \
--deploy-mode cluster \
--driver-memory 1g \
--executor-memory 1g \
--executor-cores 1 \
--master yarn \
--conf spark.serializer=org.apache.spark.serializer.KryoSerializer \
--conf spark.dynamicAllocation.maxExecutors=2 \
--conf spark.dynamicAllocation.minExecutors=2 \
--driver-class-path /opt/spark/user_files/jars \
--jars '/opt/spark/user_files/jars/postgresql-42.4.2.jar,/opt/spark/user_files/jars/spark-sql_2.12-3.0.1.jar,/opt/spark/user_files/jars/spark-sql-kafka-0-10_2.12-3.0.1.jar,/opt/spark/user_files/jars/spark-streaming_2.12-3.0.1.jar,/opt/spark/user_files/jars/spark-streaming-kafka-0-10_2.12-3.0.1.jar,/opt/spark/user_files/jars/spark-token-provider-kafka-0-10_2.12-3.0.1.jar,/opt/spark/user_files/jars/kafka-clients-3.1.1.jar,/opt/spark/user_files/jars/oms-api-pd15-0.7.1-SNAPSHOT.jar,/opt/spark/user_files/jars/semd-api-0.1.0-SNAPSHOT.jar,/opt/spark/user_files/jars/jackson-datatype-jsr310-2.15.0.jar,/opt/spark/user_files/jars/gson-2.9.1.jar,/opt/spark/user_files/jars/greenplum-connector-apache-spark-scala_2.12-2.1.4.jar' \
user_files/sandbox-kafka-streaming.jar

#--conf spark.shuffle.service.enabled=true \  # Падает!

docker-compose -f spark-client-docker-compose.yml up -d --build

docker-compose -f spark-client-docker-compose.yml ps nodemanager
docker-compose -f spark-client-docker-compose.yml ps historyserver
docker-compose -f spark-client-docker-compose.yml ps resourcemanager  !!!!
docker-compose -f spark-client-docker-compose.yml up -d resourcemanager

docker-compose -f spark-client-docker-compose.yml run -p 18080:18080 spark-client bash
docker-compose -f spark-client-docker-compose.yml run
setup-history-server.sh

#Список айпишников.
docker ps -q | xargs -n 1 docker inspect --format '{{ .Name }} {{range .NetworkSettings.Networks}} {{.IPAddress}}{{end}}' | sed 's#^/##';

# Перед выходом УБИТЬ!
# Можно в UI!
yarn application --list
yarn application --kill

http://spark:8088/cluster
http://localhost:18080/
http://localhost:9870/explorer.html#/
http://spark:8188/applicationhistory/apps/FINISHED
http://resourcemanager:8088/cluster/apps
http://resourcemanager:8088/proxy/application_1703617690363_0002/streaming/
http://resourcemanager:8088/proxy/application_1703617690363_0002/executors/
http://historyserver:8188/

















