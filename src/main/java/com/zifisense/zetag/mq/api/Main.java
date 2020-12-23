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
		if(args.length==5) {
			apiKey = args[0];
			apiSecret = args[1];
			companyCode = args[2];
			topic = args[3];
			url = args[4];
		}else {
			apiKey = "cf86bc5d68d54932a854a62626c36823";
			apiSecret  = "3c109b9463504e02bd957cd935f57cae";
			companyCode  = "cf86bc5d68d54932a854a62626c36823";
			topic = "zetag-heartbeat-all";
			url = "192.168.0.26:9093";
		}
		//建立客户端
		ZiFiClient c = ClientFactory.createClient(ClientType.KAFKA,url, apiKey, apiSecret, companyCode);
		//订阅topic
		c.subscribe(topic);
		while (true) {
			//循环处理收到的数据
			c.poll().forEach(message->{
				System.out.printf("id = %s, value = %s%n", message.getMessageId(), message.getBody());
			});
			//commit之后才能继续消费下一批次数据
			c.commit();
		}
		
	}
}
