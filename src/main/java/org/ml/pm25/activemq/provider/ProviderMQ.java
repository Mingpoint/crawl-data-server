package org.ml.pm25.activemq.provider;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProviderMQ {
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	@Autowired
	private Queue queue;
	@Autowired
	private Topic topic;
	 public void sendMessage(Object obj){
		 jmsMessagingTemplate.convertAndSend(this.queue,obj);
	 }

}
