package org.ml.pm25.spider;
import org.ml.pm25.domain.Pm25Info;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
public class SpiderCrawl implements PageProcessor{
	private Site site = Site.me()
			.setTimeOut(20000)
			.setRetryTimes(3)
			.setSleepTime(2000)
			.setCharset("UTF-8");
	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		String name = page.getHtml().xpath("//h2[@class='bi_loaction_city']/text()").get();
		String aqi = page.getHtml().xpath("//[@class='bi_aqiarea_num']/text()").get();
		String detailsurl = page.getHtml().xpath("//[@class='bi_aqiarea_num']/@href").get();
		String state = page.getHtml().xpath("//[@class='bi_aqiarea_wuran']/text()").get();
		String pm25 = page.getHtml().xpath("//[@class='pm25_span']/text()").get();
		Pm25Info info = new Pm25Info();
		info.setName(name);
		info.setState(state);
		if(null != detailsurl && !("".equals(detailsurl))){
			String trim = detailsurl.trim();
			info.setCode(trim.substring(trim.lastIndexOf("/"),trim.lastIndexOf(".")));
			info.setDetailsurl(trim);
		}
		if(null != aqi && !("".equals(aqi))) info.setAqi(Integer.parseInt(aqi.trim()));
		if(null != pm25 && !("".equals(pm25))) info.setPm25(Integer.parseInt(pm25.trim()));
		page.putField("pm25Info", info);
	}

}
