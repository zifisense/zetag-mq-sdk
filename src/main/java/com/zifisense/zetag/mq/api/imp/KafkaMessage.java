package com.zifisense.zetag.mq.api.imp;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.zifisense.zetag.mq.api.model.Message;

public class KafkaMessage extends Message {
	
	private static final String TOPIC_NAME = "topicName";
	private static final String PARTITION = "partition";
	private static final String TIME = "time";
	
	public KafkaMessage(ConsumerRecord<String, String> record) {
		setBody(record.value());
		setMessageId(String.valueOf(record.offset()));
		Map<String,String> head = new HashMap<>();
		head.put(TOPIC_NAME, record.topic());
		head.put(PARTITION, String.valueOf(record.partition()));
		head.put(TIME, String.valueOf(record.timestamp()));
	}
	
}
