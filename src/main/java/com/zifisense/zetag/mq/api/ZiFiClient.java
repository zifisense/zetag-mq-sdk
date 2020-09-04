package com.zifisense.zetag.mq.api;

import java.util.Collection;

import com.zifisense.zetag.mq.api.model.Message;

public abstract class ZiFiClient {

	public abstract void init(String url, String apiKey, String apiSecret, String companyCode);
	
	public abstract void subscribe(String topic);
	
	public abstract Collection<Message> poll();
	
	public abstract void commit();
	
}
