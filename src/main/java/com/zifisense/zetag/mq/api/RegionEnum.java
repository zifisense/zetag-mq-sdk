package com.zifisense.zetag.mq.api;

public enum RegionEnum {
	
	CN("116.62.156.176:9093");
	
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
