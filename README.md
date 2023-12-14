# Zetag-mq-sdk ZETag平台设备消息数据对接

Common lib for Zetag Java kafkaclient.

## 概述
这是对ZETag平台的kafka消息对接方式的示例代码封装kafkaclient方法

证书路径：
zetag-mq-sdk\security

### java示例
调用方式参考：
zetag-mq-sdk\src\main\java\com\zifisense\zetag\mq\api\Main.java


### python简单连接示例

```

from kafka import KafkaConsumer

if __name__ == '__main__':
	/** url/apikey/apiSecret/topic/证书名称根据实际填写 **/
    ssl_certfile = "certificate.pem"
    ssl_cafile = "CARoot.pem"
    address = "zeta1303.f3322.net:9093"
    retries = 3
    api_key = "1e3a1fc2fad94c508317edbddd168fc9"
    api_secret = "e11955fe2817445ba8cd9b6b9e292999"
    /** 如下所示topic为api key为v1版本时的格式；若api key为v2版本，则topic格式为 my_topic = api_key + "-v2" **/
    my_topic = api_key + "-v1-zetag-heartbeat-all"

    consumer = KafkaConsumer(
        'kafka_demo',
        bootstrap_servers='zeta1303.f3322.net:9093',
        group_id=api_key,
        api_version=(2, 5),
        ssl_check_hostname=False,
        ssl_certfile=ssl_certfile,
        security_protocol="SASL_SSL",
        ssl_cafile=ssl_cafile,
        sasl_mechanism="PLAIN",
        sasl_plain_username=api_key,
        sasl_plain_password=api_secret
    )
    consumer.subscribe(topics=[my_topic])
    print(consumer.topics())
    print("create consumer")
    print(consumer.partitions_for_topic(my_topic))

    for message in consumer:
        print(message)



```

### c#简单连接示例

```

using System;
using System.IO;
using System.Threading;
using Confluent.Kafka;

namespace ConsoleApp1
{
    class Program
    {
        public static void Main(string[] args)
        {
			//url/apikey/apiSecret/topic根据实际填写
            var url = "zeta1303.f3322.net:9093";
            var apiKey = "1e3a1fc2fad94c508317edbddd168fc6";
            var apiSecret = "e11955fe2817445ba8cd9b6b9e29299f";
	    // 如下所示topic为api key为v1版本时的格式；若api key为v2版本，则topic格式为 var topic = api_key + "-v2"
            var topic = "1e3a1fc2fad94c508317edbddd168fc6-v1-zetag-heartbeat-all";

            var config = new ConsumerConfig
            {
                BootstrapServers = url,
                GroupId = apiKey,
                EnableAutoCommit = false,
                MetadataMaxAgeMs = 60000,
                SecurityProtocol = SecurityProtocol.SaslSsl,
                SaslMechanism = SaslMechanism.Plain,
                SaslUsername = apiKey,
                SaslPassword = apiSecret,
                SslEndpointIdentificationAlgorithm = null,
				//证书文件路径和名称，根据实际填写
                SslCaLocation = Path.Combine(AppDomain.CurrentDomain.BaseDirectory ?? string.Empty, "App_Data", "Certificate", "CARoot.pem"),
                SslCertificateLocation = Path.Combine(AppDomain.CurrentDomain.BaseDirectory ?? string.Empty, "App_Data", "Certificate", "certificate.pem"),
            };

            using (var c = new ConsumerBuilder<Ignore, string>(config).Build())
            {
                c.Subscribe(topic);

                CancellationTokenSource cts = new CancellationTokenSource();
                Console.CancelKeyPress += (_, e) =>
                {
                    e.Cancel = true; // prevent the process from terminating.
                    cts.Cancel();
                };

                try
                {
                    while (true)
                    {
                        try
                        {
                            var cr = c.Consume(cts.Token);
                            Console.WriteLine($"Consumed message '{cr.Value}' at: '{cr.TopicPartitionOffset}'.");
                        }
                        catch (ConsumeException e)
                        {
                            Console.WriteLine($"Error occured: {e.Error.Reason}");
                        }
                    }
                }
                catch (OperationCanceledException)
                {
                    // Ensure the consumer leaves the group cleanly and final offsets are committed.
                    c.Close();
                }
            }
        }
    }
}

```

