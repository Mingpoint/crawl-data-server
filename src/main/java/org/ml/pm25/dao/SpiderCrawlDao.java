package org.ml.pm25.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ml.pm25.domain.Pm25Info;

@Mapper
public interface SpiderCrawlDao {
	public int savePm25(@Param(value="pm25Info")Pm25Info pm25Info)throws Exception;
	public List<String> queryCityCode();
	

}
