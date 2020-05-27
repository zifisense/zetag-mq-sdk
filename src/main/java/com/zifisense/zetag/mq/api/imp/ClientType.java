package com.zifisense.zetag.mq.api.imp;

public enum ClientType {
	
	KAFKA("com.zifisense.zetag.mq.api.imp.KafkaZiFiClient");
	
	ClientType(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}	
