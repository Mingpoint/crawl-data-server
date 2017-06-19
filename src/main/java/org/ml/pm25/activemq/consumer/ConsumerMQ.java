package org.ml.pm25.activemq.consumer;

import org.apache.log4j.Logger;
import org.ml.pm25.dao.SpiderCrawlDao;
import org.ml.pm25.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerMQ {
	private Logger logger = Logger.getLogger(ConsumerMQ.class);
	@Autowired
	private SpiderService spiderService;
	@JmsListener(destination = "crawl.queue.data")
	public void hadle(String code){
		try {
			spiderService.crawlData(code);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
}
