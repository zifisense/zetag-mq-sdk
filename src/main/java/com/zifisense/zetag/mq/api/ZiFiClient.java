package com.zifisense.zetag.mq.api;

import com.zifisense.zetag.mq.api.model.Message;

import java.util.Collection;

public abstract class ZiFiClient {

	public abstract void init(RegionEnum region, String apiKey, String apiSecret, String companyCode);

	public abstract void init(RegionEnum region, String apiKey, String apiSecret, String companyCode, String certPath);

	public abstract void subscribe(String topic);

	public abstract void subscribe();

	public abstract Collection<Message> poll();
	
	public abstract void commit();
	
}
