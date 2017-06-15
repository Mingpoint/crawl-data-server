package org.ml.pm25.jobs;

import java.util.List;

import org.ml.pm25.dao.SpiderCrawlDao;
import org.ml.pm25.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class Jobs {
	@Autowired
	private SpiderCrawlDao spiderCrawlDao;
	@Autowired
	private SpiderService spiderService;
	@Scheduled(cron="0 1/59 * * * ?")
	public void cronJob(){
		List<String> list = spiderCrawlDao.queryCityCode();
		if(CollectionUtils.isEmpty(list)){
			System.out.println("list为空");
			return;
		}
		System.out.println("开始定时任务");
		int i=0;
		for(String str : list){
			try {
				spiderService.crawlData(str);
				i++;
				System.out.println("code："+str+"	i："+i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("结束定时任务");
	}

}
