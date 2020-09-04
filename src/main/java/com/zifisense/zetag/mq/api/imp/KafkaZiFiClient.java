package com.zifisense.zetag.mq.api.imp;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.zifisense.zetag.mq.api.RegionEnum;
import com.zifisense.zetag.mq.api.ZiFiClient;
import com.zifisense.zetag.mq.api.model.Message;

public class KafkaZiFiClient extends ZiFiClient {

	private  KafkaConsumer<String, String> consumer;

	private KafkaConsumer<String, String> createConsumer(String url, String apiKey, String apiSecret) {
		Properties props = new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, apiKey);
		props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.setProperty(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "60000");
		props.setProperty("security.protocol", "SASL_SSL");
		props.setProperty("ssl.truststore.location", getFilePath());
		props.setProperty("ssl.truststore.password", "zifisense");
		props.setProperty("sasl.mechanism", "PLAIN");
		props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + apiKey + "\"  password=\"" + apiSecret + "\";");
		props.setProperty("ssl.endpoint.identification.algorithm", "");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		return new KafkaConsumer<>(props);
	}
	
	public void init(String url, String apiKey, String apiSecret, String companyCode) {
		this.consumer = createConsumer(url, apiKey, apiSecret);
	}
	
	public String getFilePath() {
		String path = KafkaZiFiClient.class.getClassLoader().getResource("certificate/client.truststore.jks").getPath();
		if(path.startsWith("/")) {
			return path.substring(1);
		}else if(path.startsWith("file:/")) {
			return path.substring(6);
		}else {
			return path;
		}
		
	}
	
	@Override
	public void subscribe(String topic) {
		consumer.subscribe(Pattern.compile(".*-v1-" + topic));
	}
	
	@Override
	public Collection<Message> poll() {
		Collection<Message> messages = new ArrayList<Message>();
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
		for (ConsumerRecord<String, String> record : records) {
			messages.add(new KafkaMessage(record));
		}
		return messages;
	}

	@Override
	public void commit() {
		consumer.commitSync();
	}

}
