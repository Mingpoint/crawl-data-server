package org.ml.pm25.spider;


import org.ml.pm25.dao.SpiderCrawlDao;
import org.ml.pm25.domain.Pm25Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class CustomPipelines implements Pipeline{
	@Autowired
	private SpiderCrawlDao spiderCrawlDao;
	@Override
	public void process(ResultItems result, Task task) {
		Pm25Info pm25Info = (Pm25Info)result.get("pm25Info");
		try {
			spiderCrawlDao.savePm25(pm25Info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
