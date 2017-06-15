package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ml.pm25.Application;
import org.ml.pm25.dao.SpiderCrawlDao;
import org.ml.pm25.domain.Pm25Info;
import org.ml.pm25.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)  
public class Test1 {
	@Autowired
	private SpiderCrawlDao spiderCrawlDao;
	@Autowired
	private SpiderService spiderService;
	@Test
	public void testqueryAll() throws Exception{
		List<String> queryCityCode = spiderCrawlDao.queryCityCode();
		Pm25Info pm25Info = new Pm25Info();
		pm25Info.setCode("jjj");
		spiderCrawlDao.savePm25(pm25Info);
	}
	@Test
	public void testCrawlData(){
		List<String> list = spiderCrawlDao.queryCityCode();
		if(CollectionUtils.isEmpty(list)){
			System.out.println("list为空");
			return;
		}
		for(String str : list){
			try {
				spiderService.crawlData(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
