package org.ml.pm25.dataconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;



@Configuration
@EnableConfigurationProperties(value={JdbcProperties.class})
@ConditionalOnClass(value={DruidDataSource.class})
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DataSourceConfig {
	@Autowired
	private JdbcProperties jdbcProperties;
	
	@Bean
	public DataSource getDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(jdbcProperties.getUrl());
		dataSource.setPassword(jdbcProperties.getPassword());
		dataSource.setUsername(jdbcProperties.getUsername());
		dataSource.setDriverClassName(jdbcProperties.getDriverClass());
		
		if(jdbcProperties.getInitialSize()>0){
			dataSource.setInitialSize(jdbcProperties.getInitialSize());
		}
		if(jdbcProperties.getMaxActive()>0){
			dataSource.setMaxActive(jdbcProperties.getMaxActive());
		}
		if(jdbcProperties.getMinIdle()>0){
			dataSource.setMinIdle(jdbcProperties.getMinIdle());
		}
		if(jdbcProperties.isTestOnBorrow()){
			dataSource.setTestOnBorrow(jdbcProperties.isTestOnBorrow());
		}
		return dataSource;
	}

}
