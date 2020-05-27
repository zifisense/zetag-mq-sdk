package com.zifisense.zetag.mq.api;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.zifisense.zetag.mq.api.model.Message;

public abstract class ZiFiClient {

	public abstract void init(RegionEnum region, String apiKey, String apiSecret, String companyCode);
	
	public abstract void subscribe(String topic);
	
	public abstract Collection<Message> poll();
	
	public abstract void commit();
	
}
