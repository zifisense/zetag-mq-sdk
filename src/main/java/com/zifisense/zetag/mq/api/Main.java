package com.zifisense.zetag.mq.api;
import com.zifisense.zetag.mq.api.imp.ClientFactory;
import com.zifisense.zetag.mq.api.imp.ClientType;
public class Main {
	public static void main(String[] args) {

		RegionEnum hostname;
		String apiKey;
		String apiSecret;
		String companyCode;
		String topic;
		//hostname，连接的服务器地址,实际使用需要修改\com\zifisense\zetag\mq\api\RegionEnum.java里面的连接地址
		hostname = RegionEnum.CN;
		//apiKey，当前版本一个企业只有一个apiKey，就是企业的企业编号，后续版本一个企业可能有多个apiKey
		apiKey = "";
		//相当于企业秘钥，后续版本可能也会有多个
		apiSecret  = "";
		//企业编号
		companyCode  = "";
		//描述需要获取哪些数据
		topic = "zetag-heartbeat-all";
		//建立客户端
		ZiFiClient c = ClientFactory.createClient(ClientType.KAFKA,hostname, apiKey, apiSecret, companyCode);
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
