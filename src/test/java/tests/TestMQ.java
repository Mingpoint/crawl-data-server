package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ml.pm25.Application;
import org.ml.pm25.activemq.consumer.ConsumerMQ;
import org.ml.pm25.activemq.provider.ProviderMQ;
import org.ml.pm25.domain.Pm25Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestMQ {
	@Autowired
	private ProviderMQ providerMQ;
	@Autowired
	private ConsumerMQ consumerMQ;
	@Test
	public void testMQProducter(){
		Pm25Info pm = new Pm25Info();
		pm.setAqi(100);
		pm.setCode("0001");
		providerMQ.sendMessage(pm);
	}
}
