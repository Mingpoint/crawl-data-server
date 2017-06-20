package org.ml.pm25.activemq;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfig {

	//点对点,注入bean
	@Bean
	public Queue getQueue(){
		return new ActiveMQQueue("crawl.queue.data");
	}
	//广播/订阅,注入bean
	@Bean
	public Topic getTopic(){
		return new ActiveMQTopic("crawl.topic.data");
	}
}
