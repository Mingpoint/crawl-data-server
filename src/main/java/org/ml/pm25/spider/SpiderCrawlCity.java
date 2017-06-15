package org.ml.pm25.spider;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ml.pm25.Util.Pinyins;
import org.ml.pm25.Util.PropertyUtil;
import org.ml.pm25.domain.Pm25DTO;

import com.mysql.jdbc.Connection;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author 爬取城市和省份，只用爬取一次
 *
 */
public class SpiderCrawlCity implements PageProcessor{
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
		List<String> urls = page.getHtml().xpath("/html/body/div[5]/div/div[3]/ul[1]/li/a/@href").all();
		System.out.println("urls"+urls);
		List<String> names = page.getHtml().xpath("/html/body/div[5]/div/div[3]/ul[1]/li/a/text()").all();
		System.out.println("names"+names);
		List<String> provices = page.getHtml().xpath("/html/body/div[5]/div/div[3]/ul[1]/li/span[3]/text()").all();
		System.out.println("provices"+provices);
		handlePage(page, urls, names, provices);
	}
	
	private void handlePage(Page page,List<String> urls,List<String> names,List<String> provices){
		List<Pm25DTO> list = new ArrayList<Pm25DTO>();
		Map<String, List<Pm25DTO>> mapProvice = new HashMap<String, List<Pm25DTO>>();
		for (int i = 0; i < names.size(); i++) {
			Pm25DTO pm25Info = new Pm25DTO();
			String string = urls.get(i);
			pm25Info.setCityname(names.get(i).trim());;
			pm25Info.setCode(string.substring(string.lastIndexOf("/"), string.lastIndexOf(".")));
			pm25Info.setProvicename(provices.get(i));
			pm25Info.setUrl(string.substring(string.lastIndexOf("/")+1));
			String proviceName = provices.get(i).trim();
			String hanyuPinyin = Pinyins.toHanyuPinyin(proviceName);
			List<Pm25DTO> list2 = null;
			if(mapProvice.containsKey(hanyuPinyin)){
				list2 = mapProvice.get(hanyuPinyin);
				list2.add(pm25Info);
			}else{
				list.add(pm25Info);
				list2 = new ArrayList<Pm25DTO>();
				list2.add(pm25Info);
				mapProvice.put(hanyuPinyin, list2);
			}
		}
		insertProvince(list);
		insertCity(mapProvice);
	}
	public static void insertCity(Map<String, List<Pm25DTO>> mapProvice){
		Connection conn = getConnection();
		String sql = "insert into pm25_city(code,province_code,name,url) values(?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			for(Map.Entry<String, List<Pm25DTO>> entry : mapProvice.entrySet()){
				String key = entry.getKey();
				List<Pm25DTO> value = entry.getValue();
				for(Pm25DTO pm : value){
					ps.setString(1,pm.getCode());
					ps.setString(2, key);
					ps.setString(3, pm.getCityname());
					ps.setString(4,pm.getUrl());
					ps.addBatch();
				}
			}
			ps.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != conn){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void insertProvince(List<Pm25DTO> list){
		Connection conn = getConnection();
		String sql = "insert into pm25_province(code,country_id,name) values(?,?,?)";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			for(Pm25DTO pm : list){
				ps.setString(1,Pinyins.toHanyuPinyin(pm.getProvicename()));
				ps.setInt(2, 1);
				ps.setString(3,pm.getProvicename());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != conn){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
        Spider.create(new SpiderCrawlCity()).addUrl("http://www.pm25.com/rank.html").thread(5).run();
		
	}
	public static Connection getConnection(){
		System.out.println("开始jdbc");
		Connection conn = null;
		try {
			Class.forName(PropertyUtil.getProperty("spring.datasource.driver-class-name"));
			String url = PropertyUtil.getProperty("spring.datasource.url");
			String user = PropertyUtil.getProperty("spring.datasource.username");
			String password = PropertyUtil.getProperty("spring.datasource.password");
			conn = (Connection) DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
