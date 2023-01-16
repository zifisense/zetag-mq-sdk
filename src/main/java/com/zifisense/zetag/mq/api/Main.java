package com.zifisense.zetag.mq.api;
import com.zifisense.zetag.mq.api.imp.ClientFactory;
import com.zifisense.zetag.mq.api.imp.ClientType;
public class Main {
	public static void main(String[] args) {

		RegionEnum hostname;
		String apiKey;
		String apiSecret;
		String companyCode;

		//hostname，连接的服务器地址,实际使用需要修改\com\zifisense\zetag\mq\api\RegionEnum.java里面的连接地址
		hostname = RegionEnum.CN;
		//apiKey，现在支持一个企业有多ApiKey；在V1版本为企业编码
		apiKey = "";
		// apiKey对应的apiSecret；在v1版本中，相当于企业秘钥
		apiSecret = "";
		//企业编号
		companyCode = "";

		//建立客户端
		ZiFiClient c = ClientFactory.createClient(ClientType.KAFKA,hostname, apiKey, apiSecret, companyCode);
		//订阅topic
		c.subscribe();
		/* 在v1版本中还需指定topic，该方式已被弃用
        // 描述需要获取哪些数据
        String topic= "zetag-heartbeat-all";
        c.subscribe(topic);
        */
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
