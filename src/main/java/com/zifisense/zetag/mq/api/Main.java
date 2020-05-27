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
		apiKey = "cf86bc5d68d54932a854a62626c36823";
		//�൱����ҵ��Կ�������汾����Ҳ���ж��
		apiSecret  = "3c109b9463504e02bd957cd935f57cae";
		//��ҵ���
		companyCode  = "cf86bc5d68d54932a854a62626c36823";
		//������Ҫ��ȡ��Щ����
		topic = "zetag-heartbeatall";
		//�����ͻ���
		ZiFiClient c = ClientFactory.createClient(ClientType.KAFKA,RegionEnum.CN, apiKey, apiSecret, companyCode);
		//����topic
		c.subscribe(topic);
		while (true) {
			//ѭ�������յ�������
			c.poll().forEach(message->{
				System.out.printf("id = %s, value = %s%n", message.getMessageId(), message.getBody());
			});
			//commit֮����ܼ���������һ��������
			c.commit();
		}
		
	}
}
