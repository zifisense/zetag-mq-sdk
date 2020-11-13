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
			apiKey = "1a6e2f488be240d1a82bcf78e3dd46c1";
			apiSecret  = "07db07eb5ee74c6e89258ada60954156";
			companyCode  = "1a6e2f488be240d1a82bcf78e3dd46c1";
			topic = "zetag-heartbeat-select";
			url = "test-cn.zifisense.com:9093";
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
