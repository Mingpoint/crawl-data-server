package org.ml.pm25.dataconfig;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@AutoConfigureAfter(value={DataSourceConfig.class})
public class MybatisScanner {
	@Bean
	public MapperScannerConfigurer getMapperScannerConfigurer(){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("org.ml.pm25.dao");
		return mapperScannerConfigurer;
	}
}
