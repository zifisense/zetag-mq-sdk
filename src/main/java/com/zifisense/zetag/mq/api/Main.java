package com.zifisense.zetag.mq.api;
import com.zifisense.zetag.mq.api.imp.ClientFactory;
import com.zifisense.zetag.mq.api.imp.ClientType;
public class Main {
	public static void main(String[] args) {
		String apiKey;
		String apiSecret;
		String companyCode;
		String topic;
		//apiKey����ǰ�汾һ����ҵֻ��һ��apiKey��������ҵ����ҵ��ţ������汾һ����ҵ�����ж��apiKey
		apiKey = "0d48f33151b34120a7a25d1c64387c3c";
		//�൱����ҵ��Կ�������汾����Ҳ���ж��
		apiSecret  = "227bdb21673241048c7fc86a9f72d69b";
		//��ҵ���
		companyCode  = "0d48f33151b34120a7a25d1c64387c3c";
		//������Ҫ��ȡ��Щ����
		topic = "zetag-heartbeatall";
		//�����ͻ���
		ZiFiClient c = ClientFactory.createClient(ClientType.KAFKA,RegionEnum.CN, apiKey, apiSecret, companyCode);
		//����topic
		c.subscribe(topic);
		while (true) {
			//ѭ�������յ�������
			c.poll().forEach(message->{
				System.out.printf("id = %d, value = %s%n", message.getMessageId(), message.getBody());
			});
			//commit֮����ܼ���������һ��������
			c.commit();
		}
		
	}
}
