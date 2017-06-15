package org.ml.pm25.domain;

import java.io.Serializable;

public class Pm25Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//id
	private Integer aqi;//aqi指数
	private Integer pm25;//pm25
	private String code;//城市编码
	private String state;//空气质量状态
	private String name;//城市名称
	private String detailsurl;//详情url，备用字段
	private String crawltime;//抓取时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAqi() {
		return aqi;
	}
	public void setAqi(Integer aqi) {
		this.aqi = aqi;
	}
	public Integer getPm25() {
		return pm25;
	}
	public void setPm25(Integer pm25) {
		this.pm25 = pm25;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetailsurl() {
		return detailsurl;
	}
	public void setDetailsurl(String detailsurl) {
		this.detailsurl = detailsurl;
	}
	public String getCrawltime() {
		return crawltime;
	}
	public void setCrawltime(String crawltime) {
		this.crawltime = crawltime;
	}
	
}
