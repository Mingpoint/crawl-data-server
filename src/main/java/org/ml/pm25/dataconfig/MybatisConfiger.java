package org.ml.pm25.dataconfig;
import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
public class MybatisConfiger implements TransactionManagementConfigurer{
	private Logger logger = Logger.getLogger(MybatisConfiger.class); 
	@Autowired
	private DataSource dataSource;
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory() throws Exception{
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			factory.setMapperLocations(resolver.getResources("classpath:org.ml.pm25.mapper/*.xml"));
			return factory.getObject();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	@Bean
	public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
