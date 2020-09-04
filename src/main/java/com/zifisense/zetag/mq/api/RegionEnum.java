package com.zifisense.zetag.mq.api;

public enum RegionEnum {
	
	INNER("zeta1303.f3322.net:9093"),
	OUTSIDE("test-cn.zifisense.com:9093");
	
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
