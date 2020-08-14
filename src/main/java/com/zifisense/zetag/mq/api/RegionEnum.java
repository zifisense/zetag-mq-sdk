package com.zifisense.zetag.mq.api;

public enum RegionEnum {
	
	CN("192.168.0.26:9093");
	
	RegionEnum(String url) {
		this.url = url;
	}
	
	private String url;

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}	
