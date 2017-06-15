package org.ml.pm25.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ml.pm25.spider.SpiderCrawlCity;

public class PropertyUtil {
	public static String getProperty(String name) throws IOException{
		Properties pro = new Properties();
		InputStream  file = SpiderCrawlCity.class.getClassLoader().getResourceAsStream("application.properties");
		pro.load(file);
		Object object = pro.get(name);
		if(null == object){
			return null;
		}
		return object.toString();
	}
}
