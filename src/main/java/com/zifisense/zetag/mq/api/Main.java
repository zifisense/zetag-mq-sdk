package com.zifisense.zetag.mq.api;
import com.zifisense.zetag.mq.api.imp.ClientFactory;
import com.zifisense.zetag.mq.api.imp.ClientType;
public class Main {
	public static void main(String[] args) {
		
		String apiKey;
		String apiSecret;
		String companyCode;
		String topic;
		//apiKey，当前版本一个企业只有一个apiKey，就是企业的企业编号，后续版本一个企业可能有多个apiKey
		apiKey = "27925f45b76e4045b2e534a77f8c6258";
		//相当于企业秘钥，后续版本可能也会有多个
		apiSecret  = "91c0cfeb0c85488580b95cf0bc449cfe";
		//企业编号
		companyCode  = "27925f45b76e4045b2e534a77f8c6258";
		//描述需要获取哪些数据
		topic = "device-event";
		//建立客户端
		ZiFiClient c = ClientFactory.createClient(ClientType.KAFKA,RegionEnum.CN, apiKey, apiSecret, companyCode);
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
