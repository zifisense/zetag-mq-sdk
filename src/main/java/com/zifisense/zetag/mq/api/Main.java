package com.zifisense.zetag.mq.api;
import com.zifisense.zetag.mq.api.imp.ClientFactory;
import com.zifisense.zetag.mq.api.imp.ClientType;
public class Main {
	public static void main(String[] args) {
		String apiKey;
		String apiSecret;
		String companyCode;
		String topic;
		String url;
		String path;
		if(args.length==6) {
			apiKey = args[0];
			apiSecret = args[1];
			companyCode = args[2];
			topic = args[3];
			url = args[4];
			path = args[5];
		}else {
			apiKey = "342909e75eb411ebb89f000c29533c0a";
			apiSecret  = "342909f75eb411ebb89f000c29533c0a";
			companyCode  = "342909e75eb411ebb89f000c29533c0a";
			topic = "zetag-heartbeat-all";
			url = "192.168.1.34:9094";
			path = "C:\\myHouse\\zetag-mq-sdk\\src\\main\\resources\\certificate\\client.truststore.jks";
		}
		//建立客户端
		ZiFiClient c = ClientFactory.createClient(ClientType.KAFKA,path,url, apiKey, apiSecret, companyCode);
		//订阅topic
		c.subscribe(topic);
		while (true) {
			//循环处理收到的数据
			c.poll();
			//commit之后才能继续消费下一批次数据
			//c.commit();
		}
		
	}
}
