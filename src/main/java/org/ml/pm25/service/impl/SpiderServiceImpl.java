package org.ml.pm25.service.impl;
import org.ml.pm25.service.SpiderService;
import org.ml.pm25.spider.CustomPipelines;
import org.ml.pm25.spider.SpiderCrawl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.model.OOSpider;

@Service
public class SpiderServiceImpl implements SpiderService{
	@Autowired
	private CustomPipelines customPipelines;
	public void crawlData(String code)throws Exception{
		OOSpider.create(new SpiderCrawl()).addPipeline(customPipelines)
		.addUrl("http://www.pm25.com/" + code + ".html")
		.thread(1)
		.run();
	}
}
