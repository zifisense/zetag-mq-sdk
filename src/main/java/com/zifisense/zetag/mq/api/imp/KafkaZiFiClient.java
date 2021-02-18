package com.zifisense.zetag.mq.api.imp;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.zifisense.zetag.mq.api.ZiFiClient;
import com.zifisense.zetag.mq.api.model.Message;

public class KafkaZiFiClient extends ZiFiClient {

	private  KafkaConsumer<String, String> consumer;

	private KafkaConsumer<String, String> createConsumer(String path,String url, String apiKey, String apiSecret) {
		Properties props = new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, apiKey);
		props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.setProperty(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "60000");
		props.setProperty("security.protocol", "SASL_SSL");
		props.setProperty("ssl.truststore.location", path);
		props.setProperty("ssl.truststore.password", "zifisense");
		props.setProperty("sasl.mechanism", "PLAIN");
		props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + apiKey + "\"  password=\"" + apiSecret + "\";");
		props.setProperty("ssl.endpoint.identification.algorithm", "");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		return new KafkaConsumer<>(props);
	}
	
	public void init(String path,String url, String apiKey, String apiSecret, String companyCode) {
		this.consumer = createConsumer(path,url, apiKey, apiSecret);
	}
	
	@Override
	public void subscribe(String topic) {
		consumer.subscribe(Pattern.compile(".*-v1-" + topic));
	}
	
	@Override
	public Collection<Message> poll() {
		Collection<Message> messages = new ArrayList<Message>();
		long s = System.currentTimeMillis();
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
		if(!records.isEmpty()) {
			System.out.println("time:" + new Date(System.currentTimeMillis()).toLocaleString() + ",cost:" + (System.currentTimeMillis()-s) + ",size:" + records.count());
		}
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
